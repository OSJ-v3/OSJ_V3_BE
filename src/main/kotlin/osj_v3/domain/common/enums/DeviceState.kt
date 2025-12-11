package osj_v3.domain.common.enums

enum class DeviceState(val code: Int) {
    WORKING(0),      // 작동중
    AVAILABLE(1),    // 사용 가능
    DISCONNECTED(2), // 연결 끊김
    BROKEN(3);       // 고장

    companion object {
        //숫자를 enum으로
        fun from(code: Int): DeviceState =
            entries.firstOrNull { it.code == code }
                ?: throw IllegalArgumentException("Unknown state code: $code")
    }
}