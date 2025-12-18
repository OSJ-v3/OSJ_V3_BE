package osj_v3.domain.fcm.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "notice_subscription")
class NoticeSubscriptionEntity(
    @Id
    @Column(name = "fcm_token")
    val token: String,
)