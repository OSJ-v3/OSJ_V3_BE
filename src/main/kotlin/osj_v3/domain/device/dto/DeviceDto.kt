package osj_v3.domain.device.dto

import osj_v3.domain.common.enums.DeviceState
import osj_v3.domain.common.enums.DeviceType

data class DeviceDto (
    val id: Int,
    val deviceType: DeviceType,
    val state: DeviceState
)