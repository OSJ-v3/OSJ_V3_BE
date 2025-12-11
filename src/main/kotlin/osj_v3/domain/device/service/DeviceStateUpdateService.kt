package osj_v3.domain.device.service

import org.springframework.stereotype.Service
import osj_v3.domain.common.enums.DeviceState
import osj_v3.domain.device.exception.IdNotFoundException
import osj_v3.domain.device.repository.DeviceRepository
import osj_v3.domain.socket.dto.AppStateUpdateDto
import osj_v3.domain.socket.dto.DeviceStateUpdateDto
import osj_v3.domain.socket.handler.AppSocketHandler

@Service
class DeviceStateUpdateService(
    private val deviceRepository: DeviceRepository,
    private val appSocketHandler: AppSocketHandler
) {
    fun stateUpdate(stateUpdateDto: DeviceStateUpdateDto) {
        val entity = deviceRepository.findEntityById(stateUpdateDto.id)?: throw IdNotFoundException()
        val appStateUpdateDto = AppStateUpdateDto(stateUpdateDto.id, stateUpdateDto.state)

        // app 소켓에 변경사항 전달
        appSocketHandler.sendStatusUpdate(appStateUpdateDto)

        // state 변경
        entity.state = DeviceState.from(stateUpdateDto.state)
        deviceRepository.save(entity)
    }
}