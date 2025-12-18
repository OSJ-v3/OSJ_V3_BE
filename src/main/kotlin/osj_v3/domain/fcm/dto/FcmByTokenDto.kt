package osj_v3.domain.fcm.dto

data class FcmByTokenDto(
    val token: String,
    val state: String
)