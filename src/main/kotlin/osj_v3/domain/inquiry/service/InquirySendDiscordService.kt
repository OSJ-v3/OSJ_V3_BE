package osj_v3.domain.inquiry.service

import org.springframework.stereotype.Service
import osj_v3.domain.inquiry.client.DiscordClient
import osj_v3.domain.inquiry.dto.DiscordMessage
import osj_v3.domain.inquiry.dto.InquiryRequestDto

@Service
class InquirySendDiscordService(
    private val discordClient: DiscordClient
) {
    fun sendInquiryDiscord(inquiryRequestDto: InquiryRequestDto) {
        // 1. Embed 객체 생성 (데이터 변환)
        val embed = DiscordMessage.Embed(
            title = "[${inquiryRequestDto.category.description}] ${inquiryRequestDto.title}", // 예: [버그 제보] 로그인 안됨
            description = inquiryRequestDto.content,
            color = inquiryRequestDto.category.color
        )

        // 2. 디스코드 메시지 객체 생성
        val message = DiscordMessage(
            content = "새로운 문의가 도착했습니다! 📨",
            embeds = listOf(embed)
        )

        discordClient.sendWebhook(message)
    }
}