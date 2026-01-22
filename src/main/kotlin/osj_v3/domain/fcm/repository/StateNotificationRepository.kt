package osj_v3.domain.fcm.repository

import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import osj_v3.domain.fcm.entity.StateNotificationEntity
import java.util.UUID

@Repository
interface StateNotificationRepository : JpaRepository<StateNotificationEntity, UUID>{

    fun findAllByToken(token: String): List<StateNotificationEntity>
    fun findAllByTargetDeviceId(targetDeviceId: Int): MutableList<StateNotificationEntity>
    @Transactional
    fun deleteAllByTargetDeviceId(targetDeviceId: Int)
    @Transactional
    fun deleteByTargetDeviceIdAndToken(targetDeviceId: Int, token: String)
    fun findByTargetDeviceIdAndToken(targetDeviceId: Int, token: String): StateNotificationEntity?
}