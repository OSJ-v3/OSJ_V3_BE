package osj_v3.domain.fcm.service

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.MulticastMessage
import mu.KotlinLogging
import org.springframework.stereotype.Service
import osj_v3.domain.fcm.repository.NoticeSubscriptionRepository
import osj_v3.domain.notices.dto.NoticePayloadDto

@Service
class FcmSendCreatedNotificationService(
    private val noticeSubscriptionRepository: NoticeSubscriptionRepository
) {
    private val logger = KotlinLogging.logger {}

    fun sendNotices(noticePayloadDto: NoticePayloadDto) {
        val entities = noticeSubscriptionRepository.findAll()

        // 보낼 토큰이 없으면 바로 종료
        if (entities.isEmpty()) {
            logger.info("발송할 구독자가 없습니다.")
            return
        }

        val tokens = entities.map { it.token }

        val customData = mapOf(
            "createAt" to noticePayloadDto.createAt.toString(),
            "title" to noticePayloadDto.title,
            "content" to noticePayloadDto.content
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
                    [Batch $batchIndex] 공지 알람 발송 결과
                    - 시도: ${batchTokens.size}개
                    - 성공: ${response.successCount}개
                    - 실패: ${response.failureCount}개
                    """.trimIndent()
                }

                if (response.failureCount > 0) {
                    response.responses.forEachIndexed { index, sendResponse ->
                        if (!sendResponse.isSuccessful) {
                            // [중요] 원본 tokens가 아니라 쪼개진 batchTokens에서 가져와야 함
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
                            if(exception.message == "NotRegistered"){
                                noticeSubscriptionRepository.deleteByToken(failedToken)
                            }
                            logger.info {
                                """
                                    NotRegistered 에러이기 때문에 DB에서 삭제
                                """.trimIndent()
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                logger.error("공지 알람 배치 전송 중 치명적 오류 발생: ${e.message}", e)
            }
        }
    }
}