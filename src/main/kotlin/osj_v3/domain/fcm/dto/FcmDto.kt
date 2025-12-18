package osj_v3.domain.fcm.dto

import osj_v3.domain.common.enums.DeviceState

data class FcmDto(
    val id: Int,
    val token: String,
    val expectState: DeviceState
)