package osj_v3.domain.device.service

import org.springframework.stereotype.Service
import osj_v3.domain.common.enums.DeviceState
import osj_v3.domain.device.exception.IdNotFoundException
import osj_v3.domain.device.repository.DeviceRepository
import osj_v3.domain.socket.dto.ClientStateUpdateDto
import osj_v3.domain.socket.dto.DeviceStateUpdateDto
import osj_v3.domain.socket.handler.ClientSocketHandler
import java.time.LocalDateTime

@Service
class DeviceStateUpdateService(
    private val deviceRepository: DeviceRepository,
    private val clientSocketHandler: ClientSocketHandler
) {
    fun stateUpdate(stateUpdateDto: DeviceStateUpdateDto) {
        val entity = deviceRepository.findEntityById(stateUpdateDto.id)?: throw IdNotFoundException()
        val clientStateUpdateDto = ClientStateUpdateDto(stateUpdateDto.id, stateUpdateDto.state)

        // client 소켓에 변경사항 전달
        clientSocketHandler.sendStatusUpdate(clientStateUpdateDto)

        // 시간 설정
        if(stateUpdateDto.state == DeviceState.AVAILABLE){
            entity.offTime = LocalDateTime.now()
        }
        if(stateUpdateDto.state == DeviceState.WORKING){
            entity.onTime = LocalDateTime.now()
        }

        // state 변경
        entity.state = stateUpdateDto.state
        deviceRepository.save(entity)
    }
}