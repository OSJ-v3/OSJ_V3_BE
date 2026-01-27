package osj_v3.domain.fcm.repository

import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import osj_v3.domain.fcm.entity.DeviceSubscriptionEntity
import java.util.UUID

@Repository
interface DeviceSubscriptionRepository : JpaRepository<DeviceSubscriptionEntity, UUID>{

    fun findAllByToken(token: String): List<DeviceSubscriptionEntity>
    fun findAllByTargetDeviceId(targetDeviceId: Int): MutableList<DeviceSubscriptionEntity>
    @Transactional
    fun deleteAllByTargetDeviceId(targetDeviceId: Int)
    @Transactional
    fun deleteByTargetDeviceIdAndToken(targetDeviceId: Int, token: String)
    fun findByTargetDeviceIdAndToken(targetDeviceId: Int, token: String): DeviceSubscriptionEntity?
}