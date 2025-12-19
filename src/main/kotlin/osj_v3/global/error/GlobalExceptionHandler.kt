package osj_v3.global.error

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import osj_v3.global.error.exception.OsjException

@RestControllerAdvice
class GlobalExceptionHandler() {

    @ExceptionHandler(OsjException::class)
    fun handlingOsjException(e: OsjException): ResponseEntity<ErrorResponse> {
        val code = e.errorCode
        return ResponseEntity(
            ErrorResponse(code.status, code.message),
            HttpStatus.valueOf(code.status)
        )
    }
}