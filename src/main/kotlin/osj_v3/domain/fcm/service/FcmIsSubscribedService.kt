package osj_v3.domain.fcm.service

import org.springframework.stereotype.Service
import osj_v3.domain.fcm.dto.NoticesAlertsSubscribedDto
import osj_v3.domain.fcm.repository.NoticeSubscriptionRepository

@Service
class FcmIsSubscribedService(
    private val noticeSubscriptionRepository: NoticeSubscriptionRepository
) {
    fun isSubscribed(token: String): NoticesAlertsSubscribedDto{
        return NoticesAlertsSubscribedDto(noticeSubscriptionRepository.findByToken(token) != null)
    }
}