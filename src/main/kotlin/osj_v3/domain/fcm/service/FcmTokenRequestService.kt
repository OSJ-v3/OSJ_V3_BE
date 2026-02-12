package osj_v3.domain.fcm.service

import org.springframework.stereotype.Service
import osj_v3.domain.fcm.repository.DeviceSubscriptionRepository

@Service
class FcmTokenRequestService(
    private val deviceSubscriptionRepository: DeviceSubscriptionRepository
) {
    fun tokenRequest(token: String): List<Int>{
        val entities = deviceSubscriptionRepository.findAllByToken(token)
        return entities.map { it.targetDeviceId }
    }
}