package osj_v3.domain.socket.handler

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import osj_v3.domain.device.service.DeviceFindAllService
import osj_v3.domain.socket.dto.ClientStateUpdateDto
import java.util.concurrent.ConcurrentHashMap

@Component
class ClientSocketHandler(
    private val objectMapper: ObjectMapper,
    private val deviceFindAllService: DeviceFindAllService
): TextWebSocketHandler() {
    private val sessions = ConcurrentHashMap.newKeySet<WebSocketSession>()

    override fun afterConnectionEstablished(session: WebSocketSession) {
        sessions.add(session)
        val list = deviceFindAllService.findAll()
        val jsonResponse = objectMapper.writeValueAsString(list)
        session.sendMessage(TextMessage(jsonResponse))
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        sessions.remove(session)
    }

    fun sendStatusUpdate(status: ClientStateUpdateDto) {
        val nowSessions = sessions.toList()
        for (session in nowSessions) {
            if (session.isOpen) {
                val jsonResponse = objectMapper.writeValueAsString(status)
                session.sendMessage(TextMessage(jsonResponse))
            }
            else sessions.remove(session)
        }
    }
}