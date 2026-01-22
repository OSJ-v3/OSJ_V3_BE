package osj_v3.domain.fcm.service

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.MulticastMessage
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import osj_v3.domain.common.enums.DeviceState
import osj_v3.domain.fcm.dto.StateUpdateDto
import osj_v3.domain.fcm.repository.StateNotificationRepository
import java.time.LocalDateTime

@Service
class FcmSendStateUpdateService(
    private val stateNotificationRepository: StateNotificationRepository
) {
    private val logger = KotlinLogging.logger {}

    // 삭제 로직이 포함되어 있으므로 트랜잭션 필수
    @Transactional
    fun fcmSendStateUpdate(stateUpdateDto: StateUpdateDto) {
        if(stateUpdateDto.state != DeviceState.AVAILABLE) return
        val entities = stateNotificationRepository.findAllByTargetDeviceId(stateUpdateDto.deviceId)

        // 보낼 토큰이 없으면 바로 종료
        if (entities.isEmpty()) {
            logger.info("상태 알림을 보낼 구독자가 없습니다. (DeviceId: ${stateUpdateDto.deviceId})")
            return
        }

        val tokens = entities.map { it.token }

        val customData = mapOf(
            "device_id" to stateUpdateDto.deviceId.toString(),
            "state" to stateUpdateDto.state.code.toString(),
            "prevAt" to stateUpdateDto.prevAt.toString(),
            "now" to LocalDateTime.now().toString()
        )

        tokens.chunked(500).forEachIndexed { batchIndex, batchTokens ->
            val multicastMessage = MulticastMessage.builder()
                .addAllTokens(batchTokens)
                .putAllData(customData)
                .build()

            try {
                val response = FirebaseMessaging.getInstance().sendEachForMulticast(multicastMessage)

                logger.info {
                    """
                    [Batch $batchIndex] 기기 상태 알람 발송 결과
                    - 대상 기기: ${stateUpdateDto.deviceId}
                    - 상태: ${stateUpdateDto.state}
                    - 시도: ${batchTokens.size}개
                    - 성공: ${response.successCount}개
                    - 실패: ${response.failureCount}개
                    """.trimIndent()
                }

                if (response.failureCount > 0) {
                    response.responses.forEachIndexed { index, sendResponse ->
                        if (!sendResponse.isSuccessful) {
                            // [중요] 원본 tokens가 아니라 쪼개진 batchTokens에서 인덱스로 가져옴
                            val failedToken = batchTokens[index]
                            val exception = sendResponse.exception

                            logger.error {
                                """
                                [발송 실패 상세]
                                - 토큰: $failedToken
                                - 에러 코드: ${exception.messagingErrorCode}
                                - 메시지: ${exception.message}
                                """.trimIndent()
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                logger.error("기기 상태 알람 배치 전송 중 치명적 오류 발생: ${e.message}", e)
            }
        }

        stateNotificationRepository.deleteAllByTargetDeviceId(stateUpdateDto.deviceId)
    }
}