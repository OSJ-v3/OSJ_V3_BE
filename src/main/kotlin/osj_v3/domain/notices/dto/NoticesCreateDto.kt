package osj_v3.domain.notices.dto

import jakarta.validation.constraints.NotBlank

data class NoticesCreateDto(
    @field:NotBlank(message = "제목은 비어있을 수 없습니다.")
    val title: String,

    @field:NotBlank(message = "내용은 비어있을 수 없습니다.")
    val content: String
)