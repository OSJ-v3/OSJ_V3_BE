package osj_v3.domain.device.dto

import osj_v3.domain.common.enums.DeviceState

data class DeviceDto (
    val id: Int,
    val state: DeviceState
)