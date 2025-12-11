package osj_v3.global.error.exception

enum class ErrorCode (
    val status: Int,
    val message: String
) {
    ID_NOT_FOUND(404, "id를 찾을 수 없습니다"),
}