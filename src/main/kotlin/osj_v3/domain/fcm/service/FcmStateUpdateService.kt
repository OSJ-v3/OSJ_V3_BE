package osj_v3.domain.fcm.service

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import osj_v3.domain.fcm.dto.StateUpdateDto
import osj_v3.domain.fcm.repository.StateNotificationRepository
import java.time.LocalDateTime

@Service
class FcmStateUpdateService(
    private val stateNotificationRepository: StateNotificationRepository
) {
    @Transactional
    fun fcmStateUpdate(stateUpdateDto: StateUpdateDto) {
        //엔티티조회
        val entities = stateNotificationRepository.findAllByTargetDeviceIdAndExpectState(
            targetDeviceId = stateUpdateDto.deviceId,
            expectState = stateUpdateDto.state
        )
        //엔티티삭제
        stateNotificationRepository.deleteAllByTargetDeviceIdAndExpectState(
            targetDeviceId = stateUpdateDto.deviceId,
            expectState = stateUpdateDto.state
        )
        for(entity in entities){
            val customData = mapOf(
                "device_id" to entity.targetDeviceId.toString(),
                "state" to entity.expectState.code.toString(),
                "prevAt" to stateUpdateDto.prevAt.toString(),
                "now" to LocalDateTime.now().toString()
            )
            val message = Message.builder()
                .setToken(entity.token)
                .putAllData(customData)        // 데이터 추가
                .build()
            println(FirebaseMessaging.getInstance().send(message, true))
        }
    }
}