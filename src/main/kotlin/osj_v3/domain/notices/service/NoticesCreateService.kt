package osj_v3.domain.notices.service

import org.springframework.stereotype.Service
import osj_v3.domain.notices.dto.NoticesCrateDto
import osj_v3.domain.notices.dto.NoticesDto
import osj_v3.domain.notices.entity.NoticesEntity
import osj_v3.domain.notices.repository.NoticesRepository

@Service
class NoticesCreateService(
    private val noticesRepository: NoticesRepository
) {
    fun createNotices(noticesCrateDto: NoticesCrateDto): NoticesDto{
        val entity = NoticesEntity(
            title = noticesCrateDto.title,
            contents = noticesCrateDto.content
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