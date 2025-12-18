package osj_v3.domain.fcm.dto

import osj_v3.domain.common.enums.DeviceState
import java.time.LocalDateTime

data class StateUpdateDto(
    val deviceId: Int,
    val state: DeviceState,
    val prevAt: LocalDateTime
)