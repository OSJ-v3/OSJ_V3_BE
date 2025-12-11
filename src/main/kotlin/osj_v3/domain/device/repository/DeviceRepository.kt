package osj_v3.domain.device.repository

import org.springframework.data.jpa.repository.JpaRepository
import osj_v3.domain.common.enums.DeviceLocation
import osj_v3.domain.device.entity.DeviceEntity

interface DeviceRepository : JpaRepository<DeviceEntity, Int> {
    fun findEntityById(id: Int): DeviceEntity?
    fun findByDeviceLocation(deviceLocation: DeviceLocation): List<DeviceEntity>
}
