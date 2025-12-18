package osj_v3.domain.notices.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import osj_v3.domain.fcm.dto.NoticesAlertsSubscribedDto
import osj_v3.domain.fcm.service.FcmIsSubscribedService
import osj_v3.domain.fcm.service.FcmSubscriptionService
import osj_v3.domain.notices.dto.NoticesCrateDto
import osj_v3.domain.notices.dto.NoticesDto
import osj_v3.domain.notices.service.NoticesCreateService
import osj_v3.domain.notices.service.NoticesDeleteByIdService
import osj_v3.domain.notices.service.NoticesReadAllService
import osj_v3.domain.notices.service.NoticesReadByIdService

@RestController
@RequestMapping("/notices")
class NoticesController(
    private val noticesCreateService: NoticesCreateService,
    private val noticesDeleteByIdService: NoticesDeleteByIdService,
    private val noticesReadAllService: NoticesReadAllService,
    private val noticesReadByIdService: NoticesReadByIdService,
    private val fcmSubscriptionService: FcmSubscriptionService,
    private val fcmIsSubscribedService: FcmIsSubscribedService
) {
    @PostMapping
    fun createNotices(@RequestBody noticesCrateDto: NoticesCrateDto): NoticesDto{
        return noticesCreateService.createNotices(noticesCrateDto)
    }

    @GetMapping
    fun allNotices(): List<NoticesDto>{
        return noticesReadAllService.noticesReadAll()
    }

    @GetMapping("/{id}")
    fun getNotice(@PathVariable id: Int): NoticesDto{
        return noticesReadByIdService.readById(id)
    }

    @DeleteMapping("/{id}")
    fun deleteNotice(@PathVariable id: Int){
        noticesDeleteByIdService.delete(id)
    }

    @PostMapping("/push-alerts")
    fun pushAlerts(@RequestParam("fcm_token") token: String){
        fcmSubscriptionService.subscription(token)
    }

    @GetMapping("/push-alerts")
    fun isSubscribed(@RequestParam("fcm_token") token: String): NoticesAlertsSubscribedDto{
        return fcmIsSubscribedService.isSubscribed(token)
    }
}