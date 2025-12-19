package osj_v3.domain.notices.dto

import java.time.LocalDateTime

data class NoticePayloadDto(
    val title: String,
    val content: String,
    val createAt: LocalDateTime
)