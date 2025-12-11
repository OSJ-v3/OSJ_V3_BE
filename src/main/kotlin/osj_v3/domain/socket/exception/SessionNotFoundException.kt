package osj_v3.domain.socket.exception

import osj_v3.global.error.exception.ErrorCode
import osj_v3.global.error.exception.OsjException

class SessionNotFoundException: OsjException {
    constructor(): super(ErrorCode.SESSION_NOT_FOUND)
}