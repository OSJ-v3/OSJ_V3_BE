package osj_v3.domain.fcm.service

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.MulticastMessage
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import osj_v3.domain.common.enums.DeviceState
import osj_v3.domain.fcm.dto.StateUpdateDto
import osj_v3.domain.fcm.repository.StateNotificationRepository
import java.time.LocalDateTime

@Service
class FcmStateUpdateService(
    private val stateNotificationRepository: StateNotificationRepository
) {
    fun fcmStateUpdate(stateUpdateDto: StateUpdateDto) {
        //엔티티조회
        val entities = stateNotificationRepository.findAllByTargetDeviceIdAndExpectState(
            targetDeviceId = stateUpdateDto.deviceId,
            expectState = stateUpdateDto.state
        )

        // 조회 결과가 없으면 바로 종료 (빈 리스트로 FCM 보내면 에러 날 수 있음) 몰라 재미니가 그렇대
        if (entities.isEmpty()) return

        val customData = mapOf(
            "device_id" to stateUpdateDto.deviceId.toString(),
            "state" to stateUpdateDto.state.code.toString(),
            "prevAt" to stateUpdateDto.prevAt.toString(),
            "now" to LocalDateTime.now().toString()
        )
        val tokens = entities.map { it.token }
        val multicastMessage = MulticastMessage.builder()
            .addAllTokens(tokens)
            .putAllData(customData)
            .build()
        FirebaseMessaging.getInstance().sendEachForMulticast(multicastMessage)

        //엔티티삭제
        deleteNotifications(stateUpdateDto.deviceId, stateUpdateDto.state)
    }
    private fun deleteNotifications(deviceId: Int, state: DeviceState)
        = stateNotificationRepository.deleteAllByTargetDeviceIdAndExpectState(
            targetDeviceId = deviceId,
            expectState = state
        )
}