package osj_v3.domain.inquiry.dto

import osj_v3.domain.inquiry.enum.InquiryCategory

data class InquiryRequestDto(
    val title: String,
    val content: String,
    val category: InquiryCategory
)
