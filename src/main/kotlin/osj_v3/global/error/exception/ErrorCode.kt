package osj_v3.global.error.exception

enum class ErrorCode (
    val status: Int,
    val message: String
) {
    ENUM_VALUE_NOT_FOUND(400, "enum 값을 찾을 수 없습니다."),
    ID_NOT_FOUND(404, "id를 찾을 수 없습니다."),
    DUPLICATE_NOTIFICATION(409, "알람이 이미 등록되어 있습니다.")
}