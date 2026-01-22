package osj_v3.domain.fcm.service

import org.springframework.stereotype.Service
import osj_v3.domain.fcm.repository.StateNotificationRepository

@Service
class FcmTokenRequestService(
    private val stateNotificationRepository: StateNotificationRepository
) {
    fun tokenRequest(token: String): List<Int>{
        val entities = stateNotificationRepository.findAllByToken(token)
        return entities.map { it.targetDeviceId }
    }
}