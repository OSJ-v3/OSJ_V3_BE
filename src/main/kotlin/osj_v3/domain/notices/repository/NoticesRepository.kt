package osj_v3.domain.notices.repository

import org.springframework.data.jpa.repository.JpaRepository
import osj_v3.domain.notices.entity.NoticesEntity

interface NoticesRepository : JpaRepository<NoticesEntity, Int> {
    fun findAllByOrderByCreatedAtDesc(): List<NoticesEntity>
    fun findEntityById(id:Int): NoticesEntity?
}