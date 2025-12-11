package osj_v3.domain.common.converter

import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import osj_v3.domain.common.enums.DeviceLocation

@Component
class DeviceLocationConverter : Converter<String, DeviceLocation> {
    override fun convert(source: String): DeviceLocation {
        // Enum들을 순회하며 location 값(예: "male_school")과 일치하는 것을 찾음
        return DeviceLocation.entries.firstOrNull { it.location == source }
            ?: throw IllegalArgumentException("유효하지 않은 위치입니다: $source")
    }
}