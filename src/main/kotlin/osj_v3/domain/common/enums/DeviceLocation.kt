package osj_v3.domain.common.enums

import com.fasterxml.jackson.annotation.JsonValue

enum class DeviceLocation(@JsonValue val location: String) {
    MALE_SCHOOL("male_school"),
    MALE_DORMITORY("male_dormitory"),
    FEMALE("female");
}