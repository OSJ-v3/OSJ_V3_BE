package osj_v3.domain.device.service

import org.springframework.stereotype.Service
import osj_v3.domain.common.enums.DeviceState
import osj_v3.domain.device.exception.IdNotFoundException
import osj_v3.domain.device.repository.DeviceRepository
import osj_v3.domain.socket.dto.DeviceStateUpdateDto

@Service
class DeviceStateUpdateService(
    private val deviceRepository: DeviceRepository,
) {
    fun stateUpdate(stateUpdateDto: DeviceStateUpdateDto) {
        val entity = deviceRepository.findEntityById(stateUpdateDto.id)?: throw IdNotFoundException()
        entity.state = DeviceState.from(stateUpdateDto.state)
        deviceRepository.save(entity)
    }
}