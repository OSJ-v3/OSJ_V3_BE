package osj_v3.domain.notices.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import osj_v3.domain.notices.dto.NoticesCrateDto
import osj_v3.domain.notices.dto.NoticesDto
import osj_v3.domain.notices.service.NoticesCreateService
import osj_v3.domain.notices.service.NoticesReadAllService

@RestController
@RequestMapping("/notices")
class NoticesController(
    private val noticesCreateService: NoticesCreateService,
    private val noticesReadAllService: NoticesReadAllService
) {
    @PostMapping
    fun createNotices(@RequestBody noticesCrateDto: NoticesCrateDto): NoticesDto{
        return noticesCreateService.createNotices(noticesCrateDto)
    }

    @GetMapping
    fun allNotices(): List<NoticesDto>{
        return noticesReadAllService.noticesReadAll()
    }
}