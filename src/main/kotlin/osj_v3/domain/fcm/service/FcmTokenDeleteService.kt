package osj_v3.domain.fcm.service

import org.springframework.stereotype.Service
import osj_v3.domain.fcm.dto.FcmDto
import osj_v3.domain.fcm.repository.DeviceSubscriptionRepository

@Service
class FcmTokenDeleteService(
    private val deviceSubscriptionRepository: DeviceSubscriptionRepository
) {
    fun tokenDelete(fcmDto: FcmDto){
        deviceSubscriptionRepository.deleteByTargetDeviceIdAndToken(
            targetDeviceId = fcmDto.id,
            token = fcmDto.token
        )
    }
}