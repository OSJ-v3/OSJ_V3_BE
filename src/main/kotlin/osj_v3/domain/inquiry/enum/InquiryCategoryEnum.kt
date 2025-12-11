package osj_v3.domain.inquiry.enum

enum class InquiryCategory(
    val description: String,
    val color: Int // 디스코드는 십진수(Decimal) 색상 코드를 사용합니다.
) {
    BUG("버그 제보", 0xFF0000),       // 빨강
    IMPROVEMENT("개선 요청", 0x00FF00), // 초록
    ETC("기타 문의", 0x808080);       // 회색
}