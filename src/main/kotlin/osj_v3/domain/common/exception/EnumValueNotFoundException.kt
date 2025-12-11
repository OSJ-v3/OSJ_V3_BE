package osj_v3.domain.common.exception

import osj_v3.global.error.exception.ErrorCode
import osj_v3.global.error.exception.OsjException

class EnumValueNotFoundException: OsjException(ErrorCode.ENUM_VALUE_NOT_FOUND)