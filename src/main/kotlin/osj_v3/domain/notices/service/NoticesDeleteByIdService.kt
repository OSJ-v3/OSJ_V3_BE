package osj_v3.domain.notices.service

import org.springframework.stereotype.Service
import osj_v3.domain.device.exception.IdNotFoundException
import osj_v3.domain.notices.repository.NoticesRepository

@Service
class NoticesDeleteByIdService(
    private val noticesRepository: NoticesRepository,
) {
    fun delete(id: Int) {
        noticesRepository.findEntityById(id)?:throw IdNotFoundException()
        noticesRepository.deleteById(id)
    }
}