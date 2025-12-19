package osj_v3.domain.inquiry.dto

import jakarta.validation.constraints.NotBlank
import osj_v3.domain.inquiry.enum.InquiryCategory

data class InquiryRequestDto(
    @field:NotBlank(message = "제목은 비어있을 수 없습니다.")
    val title: String,

    @field:NotBlank(message = "내용은 비어있을 수 없습니다.")
    val content: String,

    val category: InquiryCategory
)
