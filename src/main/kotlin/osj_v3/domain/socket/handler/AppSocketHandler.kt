package osj_v3.domain.socket.handler

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import osj_v3.domain.socket.dto.AppStateUpdateDto

@Component
class AppSocketHandler(
    private val objectMapper: ObjectMapper,
): TextWebSocketHandler() {
    private val sessions = mutableSetOf<WebSocketSession>()

    override fun afterConnectionEstablished(session: WebSocketSession) {
        sessions.add(session)
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        sessions.remove(session)
    }

    fun sendStatusUpdate(status: AppStateUpdateDto) {
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