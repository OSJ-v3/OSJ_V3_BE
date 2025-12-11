package osj_v3.domain.socket.handler

import osj_v3.domain.socket.dto.DeviceStateUpdateDto
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import osj_v3.domain.common.enums.DeviceLocation
import osj_v3.domain.common.enums.DeviceState
import osj_v3.domain.device.service.DeviceStateUpdateService
import osj_v3.domain.socket.exception.SessionNotFoundException
import tools.jackson.databind.ObjectMapper


@Component
class DeviceSocketHandler(
    private val objectMapper: ObjectMapper,
    private val deviceStateUpdateService: DeviceStateUpdateService
): TextWebSocketHandler() {

    private val sessions = mutableMapOf<WebSocketSession, Int>()

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val json = message.payload

        // JSON → DTO 변환 시도
        val deviceStateUpdateDto = objectMapper.readValue(json, DeviceStateUpdateDto::class.java)
        // id별 소켓 저장
        sessions[session] = deviceStateUpdateDto.id
        // 서비스 로직 실행 (오류가 없다면)
        deviceStateUpdateService.stateUpdate(deviceStateUpdateDto)
    }
    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        val deviceId = sessions.remove(session)?: throw SessionNotFoundException()

        // state 2 == DeviceState.DISCONNECTED
        val deviceStateUpdateDto = DeviceStateUpdateDto(deviceId, 2)
        deviceStateUpdateService.stateUpdate(deviceStateUpdateDto)
    }
}