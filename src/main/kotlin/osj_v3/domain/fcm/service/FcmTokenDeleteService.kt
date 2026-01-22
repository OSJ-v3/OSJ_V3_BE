package osj_v3.domain.fcm.service

import org.springframework.stereotype.Service
import osj_v3.domain.fcm.dto.FcmDto
import osj_v3.domain.fcm.repository.StateNotificationRepository

@Service
class FcmTokenDeleteService(
    private val stateNotificationRepository: StateNotificationRepository
) {
    fun tokenDelete(fcmDto: FcmDto){
        stateNotificationRepository.deleteByTargetDeviceIdAndToken(
            targetDeviceId = fcmDto.id,
            token = fcmDto.token
        )
    }
}