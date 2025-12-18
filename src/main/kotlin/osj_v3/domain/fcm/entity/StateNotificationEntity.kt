package osj_v3.domain.fcm.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table
import osj_v3.domain.common.enums.DeviceState
import java.util.UUID

@Entity
@Table(
    name = "state_notification",
    indexes = [
        Index(name = "idx_device_state", columnList = "targetDeviceId, expectState")
    ]
)

class StateNotificationEntity(
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(columnDefinition = "BINARY(16)") // MySQL UUID 효율적 저장
    val id: UUID? = null,

    @Column(nullable = false)
    val token: String,

    @Column(nullable = false)
    val targetDeviceId: Int, // 알림 대상 기기 ID

    @Column(nullable = false)
    val expectState: DeviceState
)