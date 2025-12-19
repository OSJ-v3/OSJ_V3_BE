package osj_v3.domain.device.service

import org.springframework.stereotype.Service
import osj_v3.domain.device.exception.IdNotFoundException
import osj_v3.domain.device.repository.DeviceRepository
import osj_v3.domain.fcm.dto.StateUpdateDto
import osj_v3.domain.fcm.service.FcmStateUpdateService
import osj_v3.domain.socket.dto.ClientStateUpdateDto
import osj_v3.domain.socket.dto.DeviceStateUpdateDto
import osj_v3.domain.socket.handler.ClientSocketHandler
import java.time.LocalDateTime

@Service
class DeviceStateUpdateService(
    private val deviceRepository: DeviceRepository,
    private val clientSocketHandler: ClientSocketHandler,
    private val fcmStateUpdateService: FcmStateUpdateService
) {
    fun stateUpdate(stateUpdateDto: DeviceStateUpdateDto) {
        val entity = deviceRepository.findEntityById(stateUpdateDto.id)?: throw IdNotFoundException()
        val clientStateUpdateDto = ClientStateUpdateDto(stateUpdateDto.id, stateUpdateDto.state)

        // 시간 설정
        entity.updatedAt = LocalDateTime.now()

        // state 변경
        entity.state = stateUpdateDto.state
        deviceRepository.save(entity)

        // client 소켓에 변경사항 전달
        clientSocketHandler.sendStatusUpdate(clientStateUpdateDto)
        //알람 날리기
        fcmStateUpdateService.fcmStateUpdate(StateUpdateDto(
            deviceId = entity.id,
            state = entity.state,
            prevAt = entity.updatedAt
        ))
    }
}