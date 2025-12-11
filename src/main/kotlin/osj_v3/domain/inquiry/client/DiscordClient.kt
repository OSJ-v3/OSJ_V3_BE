package osj_v3.domain.inquiry.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import osj_v3.domain.inquiry.dto.DiscordMessage

@FeignClient(name = "\${discord.name}", url = "\${discord.webhook-url}")
interface DiscordClient {
    @PostMapping
    fun sendWebhook(@RequestBody message: DiscordMessage)
}