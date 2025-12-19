package osj_v3.domain.fcm.service

import org.springframework.stereotype.Service
import osj_v3.domain.fcm.entity.NoticeSubscriptionEntity
import osj_v3.domain.fcm.repository.NoticeSubscriptionRepository

@Service
class FcmSubscriptionService(
    private val noticeSubscriptionRepository: NoticeSubscriptionRepository
) {
    fun subscription(token: String){
        noticeSubscriptionRepository.save(NoticeSubscriptionEntity(token))
    }
}