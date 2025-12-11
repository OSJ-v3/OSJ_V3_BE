package osj_v3.domain.device.service

import org.springframework.stereotype.Service
import osj_v3.domain.common.enums.DeviceLocation
import osj_v3.domain.device.dto.DeviceDto
import osj_v3.domain.device.repository.DeviceRepository

@Service
class DeviceFindByLocationService(
    private val deviceRepository: DeviceRepository,
) {
    fun findDevices(deviceLocation: DeviceLocation): List<DeviceDto> {
        val deviceEntities = deviceRepository.findByDeviceLocation(deviceLocation)
        return deviceEntities.map { DeviceDto(it.id, it.deviceType, it.state) }
    }
}