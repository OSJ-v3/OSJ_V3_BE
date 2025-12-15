package osj_v3.domain.device.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import osj_v3.domain.common.enums.DeviceState
import java.time.LocalDateTime

@Entity
@Table(name = "devices")

class DeviceEntity(

    @Id
    @Column
    val id: Int,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var state: DeviceState,

    @Column(nullable = false)
    var onTime: LocalDateTime,

    @Column(nullable = false)
    var offTime: LocalDateTime,
)
