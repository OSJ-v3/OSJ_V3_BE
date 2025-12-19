package osj_v3.domain.notices.service

import mu.KotlinLogging
import org.springframework.stereotype.Service
import osj_v3.domain.fcm.service.FcmSandNoticesService
import osj_v3.domain.notices.dto.NoticePayloadDto
import osj_v3.domain.notices.dto.NoticesCreateDto
import osj_v3.domain.notices.dto.NoticesDto
import osj_v3.domain.notices.entity.NoticesEntity
import osj_v3.domain.notices.repository.NoticesRepository

@Service
class NoticesCreateService(
    private val noticesRepository: NoticesRepository,
    private val fcmSandNoticesService: FcmSandNoticesService
) {
    fun createNotices(noticesCreateDto: NoticesCreateDto): NoticesDto{
        val entity = NoticesEntity(
            title = noticesCreateDto.title,
            contents = noticesCreateDto.content
        )
        noticesRepository.save(entity)

        try {
            fcmSandNoticesService.sendNotices(
                NoticePayloadDto(
                    createAt = entity.createdAt,
                    title = entity.title,
                    content = entity.contents
                )
            )
        }
        catch (e: Exception) {
            val logger = KotlinLogging.logger {}
            logger.error(e.message, e)
        }

        return NoticesDto(
            title = entity.title,
            content = entity.contents,
            id = entity.id,
            createdAt = entity.createdAt.toString()
        )
    }
}