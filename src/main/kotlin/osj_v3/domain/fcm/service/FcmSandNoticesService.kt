package osj_v3.domain.fcm.service

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.MulticastMessage
import mu.KotlinLogging
import org.springframework.stereotype.Service
import osj_v3.domain.fcm.repository.NoticeSubscriptionRepository
import osj_v3.domain.notices.dto.NoticePayloadDto

@Service
class FcmSendNoticesService(
    private val noticeSubscriptionRepository: NoticeSubscriptionRepository
) {
    fun sendNotices(noticePayloadDto: NoticePayloadDto) {
        val entities = noticeSubscriptionRepository.findAll()

        val customData = mapOf(
            "createAt" to noticePayloadDto.createAt.toString(),
            "title" to noticePayloadDto.title,
            "content" to noticePayloadDto.content
        )

        val tokens = entities.map { it.token }

        tokens.chunked(500).forEach {
            val multicastMessage = MulticastMessage.builder()
                .addAllTokens(it)
                .putAllData(customData)
                .build()
            try {
                val response = FirebaseMessaging.getInstance().sendEachForMulticast(multicastMessage)

                println("공지 알람 발송")
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
            } catch (e: Exception) {
                val logger = KotlinLogging.logger {}
                logger.error("공지 알람 전송 실패: ${e.message}", e)
            }
        }
    }
}