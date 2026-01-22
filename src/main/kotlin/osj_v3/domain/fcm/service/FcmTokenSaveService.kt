package osj_v3.domain.fcm.service

import org.springframework.stereotype.Service
import osj_v3.domain.fcm.dto.FcmDto
import osj_v3.domain.fcm.entity.StateNotificationEntity
import osj_v3.domain.fcm.exception.DuplicateNotificationException
import osj_v3.domain.fcm.repository.StateNotificationRepository

@Service
class FcmTokenSaveService(
    private val stateNotificationRepository: StateNotificationRepository
) {
    fun tokenSave(fcmDto: FcmDto){
        val entity = stateNotificationRepository.findByTargetDeviceIdAndToken(
            targetDeviceId = fcmDto.id,
            token = fcmDto.token
        )
        if(entity != null) throw DuplicateNotificationException()

        stateNotificationRepository.save(
            StateNotificationEntity(
                targetDeviceId = fcmDto.id,
                token = fcmDto.token
            )
        )
    }
}