package osj_v3.domain.device.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import lombok.NoArgsConstructor
import osj_v3.domain.common.enums.DeviceLocation
import osj_v3.domain.common.enums.DeviceState
import osj_v3.domain.common.enums.DeviceType
import java.time.LocalDateTime

@Entity
@Table(name = "devices")

class DeviceEntity(

    @Id
    @Column()
    var id: Int? = null,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var state: DeviceState,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var deviceType: DeviceType,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val deviceLocation: DeviceLocation,

    @Column(nullable = false)
    var onTime: LocalDateTime,

    @Column(nullable = false)
    var offTime: LocalDateTime,
)
