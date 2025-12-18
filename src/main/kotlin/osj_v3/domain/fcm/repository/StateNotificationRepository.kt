package osj_v3.domain.fcm.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import osj_v3.domain.common.enums.DeviceState
import osj_v3.domain.fcm.entity.StateNotificationEntity
import java.util.UUID

@Repository
interface StateNotificationRepository : JpaRepository<StateNotificationEntity, UUID>{
    fun deleteAllByTargetDeviceIdAndExpectState(
        targetDeviceId: Int,
        expectState: DeviceState
    ): List<StateNotificationEntity>
    fun findByTargetDeviceIdAndExpectStateAndToken(
        targetDeviceId: Int,
        expectState: DeviceState,
        token: String
    ): StateNotificationEntity?
    fun deleteByTargetDeviceIdAndExpectStateAndToken(
        targetDeviceId: Int,
        expectState: DeviceState,
        token: String
    )
    fun findAllByToken(token: String): List<StateNotificationEntity>
}