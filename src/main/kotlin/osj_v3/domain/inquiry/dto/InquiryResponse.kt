package osj_v3.domain.inquiry.dto

data class DiscordMessage(
    val content: String? = null, // 일반 텍스트 (옵션)
    val embeds: List<Embed>? = null
) {
    data class Embed(
        val title: String,
        val description: String,
        val color: Int
    )
}