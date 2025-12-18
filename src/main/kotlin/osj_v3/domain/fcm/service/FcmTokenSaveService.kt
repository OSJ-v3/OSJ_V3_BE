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
        stateNotificationRepository.findByTargetDeviceIdAndExpectStateAndToken(
            targetDeviceId = fcmDto.id,
            expectState = fcmDto.expectState,
            token = fcmDto.token
        )?: throw DuplicateNotificationException()

        stateNotificationRepository.save(
            StateNotificationEntity(
                targetDeviceId = fcmDto.id,
                expectState = fcmDto.expectState,
                token = fcmDto.token
            )
        )
    }
}