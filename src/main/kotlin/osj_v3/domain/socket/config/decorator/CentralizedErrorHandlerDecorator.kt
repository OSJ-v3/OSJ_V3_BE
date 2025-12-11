package osj_v3.domain.socket.config.decorator

import org.slf4j.LoggerFactory
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.WebSocketMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.WebSocketHandlerDecorator
import osj_v3.global.error.exception.OsjException
import tools.jackson.core.exc.StreamReadException
import tools.jackson.databind.exc.MismatchedInputException

class CentralizedErrorHandlerDecorator(
    private val delegate: WebSocketHandler
) : WebSocketHandlerDecorator(delegate) {
    private val logger = LoggerFactory.getLogger(CentralizedErrorHandlerDecorator::class.java)

    override fun handleMessage(session: WebSocketSession, message: WebSocketMessage<*>) {
        try {
            // 원본 핸들러의 로직을 실행
            super.handleMessage(session, message)
        } catch (e: OsjException) {
            // OsjException (IdNotFoundException 포함) 처리
            handleOsjException(session, e)
        } catch (e: MismatchedInputException) {
            //json 파싱 실패
            handleMismatchedInputException(session, e)
        } catch (e: StreamReadException){
            handleStreamReadException(session, e)
        } catch (e: Exception) {
            // 기타 모든 예상치 못한 오류 처리 (JsonProcessingException 등)
            handleGenericException(session, e)
        }
    }

    private fun handleOsjException(session: WebSocketSession, e: OsjException) {
        val errorCode = e.errorCode
        val responseMessage = """
            {"status": ${errorCode.status}, "code": "${errorCode.name}", "message": "${errorCode.message}"}
        """.trimIndent()

        // 클라이언트에게 오류 JSON 전송
        if (session.isOpen) {
            session.sendMessage(TextMessage(responseMessage))
        }
    }

    private fun handleMismatchedInputException(session: WebSocketSession, e: MismatchedInputException) {
        val responseMessage = """
            {"status": 400, "code": "BAD_REQUEST", "message": "잘못된 형식 입니다."}
        """.trimIndent()

        if (session.isOpen) {
            session.sendMessage(TextMessage(responseMessage))
        }
    }

    private fun handleStreamReadException(session: WebSocketSession, e: StreamReadException) {
        val responseMessage = """
            {"status": 400, "code": "BAD_REQUEST", "message": "잘못된 JSON 입니다."}
        """.trimIndent()

        if (session.isOpen) {
            session.sendMessage(TextMessage(responseMessage))
        }
    }

    private fun handleGenericException(session: WebSocketSession, e: Exception) {
        val responseMessage = """
            {"status": 500, "code": "SERVER_ERROR", "message": "서버 내부 처리 오류가 발생했습니다."}
        """.trimIndent()

        if (session.isOpen) {
            session.sendMessage(TextMessage(responseMessage))
        }
        logger.error(e.message, e)
    }
}