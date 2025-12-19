package osj_v3.domain.inquiry.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import osj_v3.domain.inquiry.dto.InquiryRequestDto
import osj_v3.domain.inquiry.service.InquirySendDiscordService

@RestController
@RequestMapping("/inquiry")
class InquiryController(
    private val InquirySendDiscordService: InquirySendDiscordService
) {
    @PostMapping
    fun receiveInquiry(@RequestBody inquiryRequestDto: InquiryRequestDto) {
        InquirySendDiscordService.sandInquiryDiscord(inquiryRequestDto)
    }
}