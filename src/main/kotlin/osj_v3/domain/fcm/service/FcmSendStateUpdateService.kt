package osj_v3.domain.fcm.service

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.MulticastMessage
import org.springframework.stereotype.Service
import osj_v3.domain.common.enums.DeviceState
import osj_v3.domain.fcm.dto.StateUpdateDto
import osj_v3.domain.fcm.repository.StateNotificationRepository
import java.time.LocalDateTime

@Service
class FcmSendStateUpdateService(
    private val stateNotificationRepository: StateNotificationRepository
) {
    fun fcmSendStateUpdate(stateUpdateDto: StateUpdateDto) {
        println("fcmSendStateUpdate" + "  "+ stateUpdateDto.state + "  " + stateUpdateDto.deviceId)
        //엔티티조회
        val entities = stateNotificationRepository.findAllByTargetDeviceIdAndExpectState(
            targetDeviceId = stateUpdateDto.deviceId,
            expectState = stateUpdateDto.state
        )

        // 조회 결과가 없으면 바로 종료 (빈 리스트로 FCM 보내면 에러 날 수 있음) 몰라 재미니가 그렇대
        if (entities.isEmpty()) return

        val customData = mapOf(
            "device_id" to stateUpdateDto.deviceId.toString(),
            "state" to stateUpdateDto.state.code.toString(),
            "prevAt" to stateUpdateDto.prevAt.toString(),
            "now" to LocalDateTime.now().toString()
        )
        val tokens = entities.map { it.token }
        val multicastMessage = MulticastMessage.builder()
            .addAllTokens(tokens)
            .putAllData(customData)
            .build()
        val response = FirebaseMessaging.getInstance().sendEachForMulticast(multicastMessage)
        println(response)
        // 2. 전체 성공/실패 횟수를 확인합니다.

        println("기기 상태 알람 발송")
        println("총 발송 시도: ${tokens.size}개")
        println("성공: ${response.successCount}개")
        println("실패: ${response.failureCount}개")

        // 3. 실패했다면 '왜' 실패했는지 응답을 뜯어봅니다.
        if (response.failureCount > 0) {
            response.responses.forEachIndexed { index, sendResponse ->
                if (!sendResponse.isSuccessful) {
                    // 어떤 토큰이 에러가 났고, 에러 내용이 무엇인지 로그를 남깁니다.
                    val failedToken = tokens[index]
                    val errorCode = sendResponse.exception.messagingErrorCode
                    val errorMessage = sendResponse.exception.message
                    println("실패 토큰: $failedToken")
                    println("에러 코드: $errorCode") // 예: UNREGISTERED, INVALID_ARGUMENT
                    println("에러 메시지: $errorMessage")
                }
            }
        } else {
            println("모든 메시지가 FCM 서버에 정상적으로 접수되었습니다.")
        }

        //엔티티삭제
        deleteNotifications(stateUpdateDto.deviceId, stateUpdateDto.state)
    }
    private fun deleteNotifications(deviceId: Int, state: DeviceState)
        = stateNotificationRepository.deleteAllByTargetDeviceIdAndExpectState(
            targetDeviceId = deviceId,
            expectState = state
        )
}