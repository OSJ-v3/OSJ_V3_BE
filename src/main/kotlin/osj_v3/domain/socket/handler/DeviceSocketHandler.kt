package osj_v3.domain.socket.handler

import osj_v3.domain.socket.dto.DeviceStateUpdateDto
import org.springframework.stereotype.Component
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import osj_v3.domain.device.service.DeviceStateUpdateService
import tools.jackson.databind.ObjectMapper


@Component
class DeviceSocketHandler(
    private val objectMapper: ObjectMapper,
    private val deviceStateUpdateService: DeviceStateUpdateService
): TextWebSocketHandler() {

    private val sessions = mutableSetOf<WebSocketSession>()

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        sessions.add(session)
        val json = message.payload

        // JSON → DTO 변환 시도
        val deviceStateUpdateDto = objectMapper.readValue(json, DeviceStateUpdateDto::class.java)
        // 서비스 로직 실행 (오류가 없다면)
        deviceStateUpdateService.stateUpdate(deviceStateUpdateDto)
    }
}