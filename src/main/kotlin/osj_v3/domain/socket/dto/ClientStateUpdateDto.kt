package osj_v3.domain.socket.dto

import osj_v3.domain.common.enums.DeviceState

data class ClientStateUpdateDto(
    val id: Int,
    val state: DeviceState
)
