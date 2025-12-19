package osj_v3.domain.fcm.service

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.MulticastMessage
import org.springframework.stereotype.Service
import osj_v3.domain.fcm.repository.NoticeSubscriptionRepository
import osj_v3.domain.notices.dto.NoticePayloadDto

@Service
class FcmSandNoticesService(
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

        val multicastMessage = MulticastMessage.builder()
            .addAllTokens(tokens)
            .putAllData(customData)
            .build()
        FirebaseMessaging.getInstance().sendEachForMulticast(multicastMessage)
    }
}