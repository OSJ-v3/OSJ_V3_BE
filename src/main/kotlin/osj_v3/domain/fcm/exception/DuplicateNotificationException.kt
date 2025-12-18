package osj_v3.domain.fcm.exception

import osj_v3.global.error.exception.ErrorCode
import osj_v3.global.error.exception.OsjException

class DuplicateNotificationException: OsjException(ErrorCode.DUPLICATE_NOTIFICATION)