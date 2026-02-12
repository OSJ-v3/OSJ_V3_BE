package osj_v3.domain.fcm.service

import org.springframework.stereotype.Service
import osj_v3.domain.fcm.dto.FcmDto
import osj_v3.domain.fcm.entity.DeviceSubscriptionEntity
import osj_v3.domain.fcm.exception.DuplicateNotificationException
import osj_v3.domain.fcm.repository.DeviceSubscriptionRepository

@Service
class FcmTokenSaveService(
    private val deviceSubscriptionRepository: DeviceSubscriptionRepository
) {
    fun tokenSave(fcmDto: FcmDto){
        val entity = deviceSubscriptionRepository.findByTargetDeviceIdAndToken(
            targetDeviceId = fcmDto.id,
            token = fcmDto.token
        )
        if(entity != null) throw DuplicateNotificationException()

        deviceSubscriptionRepository.save(
            DeviceSubscriptionEntity(
                targetDeviceId = fcmDto.id,
                token = fcmDto.token
            )
        )
    }
}