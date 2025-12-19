package osj_v3.domain.device.service

import mu.KotlinLogging
import org.springframework.stereotype.Service
import osj_v3.domain.device.exception.IdNotFoundException
import osj_v3.domain.device.repository.DeviceRepository
import osj_v3.domain.fcm.dto.StateUpdateDto
import osj_v3.domain.fcm.service.FcmSendStateUpdateService
import osj_v3.domain.socket.dto.ClientStateUpdateDto
import osj_v3.domain.socket.dto.DeviceStateUpdateDto
import osj_v3.domain.socket.handler.ClientSocketHandler
import java.time.LocalDateTime

@Service
class DeviceStateUpdateService(
    private val deviceRepository: DeviceRepository,
    private val clientSocketHandler: ClientSocketHandler,
    private val fcmSendStateUpdateService: FcmSendStateUpdateService
) {
    fun stateUpdate(stateUpdateDto: DeviceStateUpdateDto) {
        val entity = deviceRepository.findEntityById(stateUpdateDto.id)?: throw IdNotFoundException()
        val clientStateUpdateDto = ClientStateUpdateDto(stateUpdateDto.id, stateUpdateDto.state)

        val prevAt = entity.updatedAt
        // 시간 설정
        entity.updatedAt = LocalDateTime.now()

        // state 변경
        entity.state = stateUpdateDto.state
        deviceRepository.save(entity)

        // fcm 전송에서 에러가 났을때 다른 클라에게 메세지가 가면 안됌 글서 try 씀
        try {
            //알람 날리기
            fcmSendStateUpdateService.fcmSendStateUpdate(
                StateUpdateDto(
                    deviceId = entity.id,
                    state = entity.state,
                    prevAt = prevAt
                )
            )
        } catch (e: Exception) {
            val logger = KotlinLogging.logger {}
            logger.error(e.message, e)
        }

        // client 소켓에 변경사항 전달
        clientSocketHandler.sendStatusUpdate(clientStateUpdateDto)
    }
}