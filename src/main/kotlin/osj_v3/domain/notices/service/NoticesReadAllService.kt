package osj_v3.domain.notices.service

import org.springframework.stereotype.Service
import osj_v3.domain.notices.dto.NoticesDto
import osj_v3.domain.notices.repository.NoticesRepository

@Service
class NoticesReadAllService(
    private val noticesRepository: NoticesRepository
) {
    fun noticesReadAll() = noticesRepository.findAllByOrderByCreatedAtDesc().map {
        noticesEntity ->
        NoticesDto(
            title = noticesEntity.title,
            content = noticesEntity.contents,
            id = noticesEntity.id!!,
            createdAt = noticesEntity.createdAt.toString()
        )
    }
}