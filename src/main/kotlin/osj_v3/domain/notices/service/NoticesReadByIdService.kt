package osj_v3.domain.notices.service

import org.springframework.stereotype.Service
import osj_v3.domain.device.exception.IdNotFoundException
import osj_v3.domain.notices.dto.NoticesDto
import osj_v3.domain.notices.repository.NoticesRepository

@Service
class NoticesReadByIdService(
    private val noticesRepository: NoticesRepository,
) {
    fun readById(id: Int): NoticesDto{
        val entity = noticesRepository.findEntityById(id)?: throw IdNotFoundException()
        return NoticesDto(
            title = entity.title,
            content = entity.contents,
            id = entity.id,
            createdAt = entity.createdAt.toString()
        )
    }
}