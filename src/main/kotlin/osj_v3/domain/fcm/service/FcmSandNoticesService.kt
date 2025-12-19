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
                FirebaseMessaging.getInstance().sendEachForMulticast(multicastMessage)
            } catch (e: Exception) {
                val logger = KotlinLogging.logger {}
                logger.error("공지 알람 전송 실패: ${e.message}", e)
            }
        }
    }
}