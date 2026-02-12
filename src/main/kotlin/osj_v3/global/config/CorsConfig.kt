package osj_v3.global.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
class CorsConfig(
    @Value("\${cors.allowed-origins}")
    private val allowedOrigins: String
): WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        val origins = allowedOrigins.split(",").toTypedArray()

        registry.addMapping("/**") // 모든 경로에 대해
            .allowedOriginPatterns("*") //개발용
            .allowCredentials(true) //개발용
            //개발용 .allowedOrigins(*origins) // 허용할 프론트엔드 도메인
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 허용할 메소드
            //개발용 .allowedHeaders("*") // 모든 헤더 허용
            .allowCredentials(true) // 쿠키/인증 정보 포함 허용
    }
}