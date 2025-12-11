package osj_v3.domain.device.exception

import osj_v3.global.error.exception.ErrorCode
import osj_v3.global.error.exception.OsjException

class IdNotFoundException: OsjException(ErrorCode.ID_NOT_FOUND)