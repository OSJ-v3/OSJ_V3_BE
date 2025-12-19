package osj_v3.domain.fcm.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import jakarta.annotation.PostConstruct
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets

@Configuration
class FirebaseConfig {

    @Value("\${fcm.key}")
    lateinit var fcmKey: String

    @PostConstruct
    fun init() {
        try {
            if (FirebaseApp.getApps().isEmpty()) {
                // 1. JSON 문자열을 InputStream으로 변환
                // 주의: 환경변수 주입 시 \n이 문자로 들어오면 실제 개행으로 바꿔줘야 할 수 있음
                val keyStream = ByteArrayInputStream(
                    fcmKey.replace("\\n", "\n").toByteArray(StandardCharsets.UTF_8)
                )

                // 2. 옵션 설정 및 초기화
                val options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(keyStream))
                    .build()

                FirebaseApp.initializeApp(options)
            }
        } catch (e: Exception) {
            val logger = KotlinLogging.logger {}
            logger.error("FCM 초기화 실패: ${e.message}", e)
            throw RuntimeException("FCM 초기화 에러: ${e.message}")
        }
    }
}