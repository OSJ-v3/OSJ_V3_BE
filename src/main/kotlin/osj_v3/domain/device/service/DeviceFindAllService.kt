package osj_v3.domain.device.service

import org.springframework.stereotype.Service
import osj_v3.domain.device.dto.DeviceDto
import osj_v3.domain.device.repository.DeviceRepository

@Service
class DeviceFindAllService(
    private val deviceRepository: DeviceRepository
) {
    fun findAll(): List<DeviceDto>{
        return deviceRepository.findAll().map { deviceEntity -> DeviceDto(deviceEntity.id, deviceEntity.state) }
    }
}