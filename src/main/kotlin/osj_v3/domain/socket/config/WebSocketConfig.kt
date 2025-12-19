package osj_v3.domain.socket.config

import org.springframework.beans.factory.annotation.Value
import osj_v3.domain.socket.handler.DeviceSocketHandler
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry
import osj_v3.domain.socket.config.decorator.CentralizedErrorHandlerDecorator
import osj_v3.domain.socket.handler.ClientSocketHandler

@Configuration
@EnableWebSocket
class WebSocketConfig(
    private val deviceSocketHandler: DeviceSocketHandler,
    private val clientSocketHandler: ClientSocketHandler,
    @Value("\${cors.allowed-origins}")
    private val allowedOrigins: String
): WebSocketConfigurer {

    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        // 데코레이터 패턴
        // 데코레이터로 감싸서 입-출력 값 조정
        // 에러처리 방식으로 사용
        val decoratedDeviceSocketHandler = CentralizedErrorHandlerDecorator(deviceSocketHandler)
        val decoratedClientSocketHandler = CentralizedErrorHandlerDecorator(clientSocketHandler)
        val origins = allowedOrigins.split(",").toTypedArray()

        registry.addHandler(decoratedDeviceSocketHandler, "/device")
            .setAllowedOrigins(*origins)
        registry.addHandler(decoratedClientSocketHandler, "/client")
            .setAllowedOrigins(*origins)
    }
}