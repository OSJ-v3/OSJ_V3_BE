package osj_v3.domain.fcm.service

import org.springframework.stereotype.Service
import osj_v3.domain.fcm.entity.NoticeSubscriptionEntity
import osj_v3.domain.fcm.exception.DuplicateNotificationException
import osj_v3.domain.fcm.repository.NoticeSubscriptionRepository

@Service
class FcmSubscriptionService(
    private val noticeSubscriptionRepository: NoticeSubscriptionRepository,
    private val fcmIsSubscribedService: FcmIsSubscribedService
) {
    fun subscription(token: String){
        if(fcmIsSubscribedService.isSubscribed(token).isSubscribed) {
            throw DuplicateNotificationException()
        }
        noticeSubscriptionRepository.save(NoticeSubscriptionEntity(token))
    }
}