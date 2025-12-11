package osj_v3.domain.common.enums

import com.fasterxml.jackson.annotation.JsonValue

enum class DeviceType(@JsonValue val type: String) {
    DRY("DRY"),
    WASH("WASH");
}