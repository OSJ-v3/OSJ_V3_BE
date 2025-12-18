package osj_v3.domain.fcm.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import osj_v3.domain.fcm.entity.NoticeSubscriptionEntity

@Repository
interface NoticeSubscriptionRepository : JpaRepository<NoticeSubscriptionEntity, String> {
    fun findByToken(token: String): NoticeSubscriptionEntity?
}