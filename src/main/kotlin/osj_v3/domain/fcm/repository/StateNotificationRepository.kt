package osj_v3.domain.fcm.repository

import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import osj_v3.domain.common.enums.DeviceState
import osj_v3.domain.fcm.entity.StateNotificationEntity
import java.util.UUID

@Repository
interface StateNotificationRepository : JpaRepository<StateNotificationEntity, UUID>{
    fun findAllByTargetDeviceIdAndExpectState(
        targetDeviceId: Int,
        expectState: DeviceState
    ): List<StateNotificationEntity>

    @Transactional
    fun deleteAllByTargetDeviceIdAndExpectState(
        targetDeviceId: Int,
        expectState: DeviceState
    )

    fun findByTargetDeviceIdAndExpectStateAndToken(
        targetDeviceId: Int,
        expectState: DeviceState,
        token: String
    ): StateNotificationEntity?

    @Transactional
    fun deleteByTargetDeviceIdAndExpectStateAndToken(
        targetDeviceId: Int,
        expectState: DeviceState,
        token: String
    )

    fun findAllByToken(token: String): List<StateNotificationEntity>
}