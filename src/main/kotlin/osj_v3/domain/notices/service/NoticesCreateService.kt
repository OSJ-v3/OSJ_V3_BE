package osj_v3.domain.notices.service

import org.springframework.stereotype.Service
import osj_v3.domain.notices.dto.NoticesCreateDto
import osj_v3.domain.notices.dto.NoticesDto
import osj_v3.domain.notices.entity.NoticesEntity
import osj_v3.domain.notices.repository.NoticesRepository

@Service
class NoticesCreateService(
    private val noticesRepository: NoticesRepository
) {
    fun createNotices(noticesCreateDto: NoticesCreateDto): NoticesDto{
        val entity = NoticesEntity(
            title = noticesCreateDto.title,
            contents = noticesCreateDto.content
        )
        noticesRepository.save(entity)
        return NoticesDto(
            title = entity.title,
            content = entity.contents,
            id = entity.id,
            createdAt = entity.createdAt.toString()
        )
    }
}