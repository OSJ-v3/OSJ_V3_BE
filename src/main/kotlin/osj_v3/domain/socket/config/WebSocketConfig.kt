package osj_v3.domain.socket.config

import osj_v3.domain.socket.handler.DeviceSocketHandler
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry
import osj_v3.domain.socket.config.decorator.CentralizedErrorHandlerDecorator
import osj_v3.domain.socket.handler.AppSocketHandler

@Configuration
@EnableWebSocket
class WebSocketConfig(
    private val deviceSocketHandler: DeviceSocketHandler,
    private val appSocketHandler: AppSocketHandler
): WebSocketConfigurer {

    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        // 데코레이터 패턴
        // 데코레이터로 감싸서 입-출력 값 조정
        // 에러처리 방식으로 사용
        val decoratedDeviceSocketHandler = CentralizedErrorHandlerDecorator(deviceSocketHandler)
        val decoratedAppSocketHandler = CentralizedErrorHandlerDecorator(appSocketHandler)

        registry.addHandler(decoratedDeviceSocketHandler, "/device")
            .setAllowedOrigins("*")
        registry.addHandler(decoratedAppSocketHandler, "/app")
            .setAllowedOrigins("*")
    }
}