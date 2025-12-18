package osj_v3.domain.notices.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Lob
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "Notices")

class NoticesEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @Column(name = "title", nullable = false, length = 255)
    var title: String,

    @Lob // 대용량 텍스트를 저장할 때 사용 (VARCHAR(255)보다 큰 TEXT 타입)
    @Column(name = "contents", nullable = false)
    var contents: String, // 공지사항 내용

    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
)