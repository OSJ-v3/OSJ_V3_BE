package osj_v3.global.error.exception

abstract class OsjException (
    val errorCode: ErrorCode
) : RuntimeException()