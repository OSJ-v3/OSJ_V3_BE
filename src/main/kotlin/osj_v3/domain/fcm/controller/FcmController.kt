package osj_v3.domain.fcm.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import osj_v3.domain.fcm.dto.FcmByTokenDto
import osj_v3.domain.fcm.dto.FcmDto
import osj_v3.domain.fcm.service.FcmTokenDeleteService
import osj_v3.domain.fcm.service.FcmTokenRequestService
import osj_v3.domain.fcm.service.FcmTokenSaveService

@RestController
@RequestMapping("push-alerts")
class FcmController(
    private val fcmTokenSaveService: FcmTokenSaveService,
    private val fcmTokenDeleteService: FcmTokenDeleteService,
    private val fcmTokenRequestService: FcmTokenRequestService
) {
    @PostMapping
    fun tokenSave(fcmDto: FcmDto) {
        fcmTokenSaveService.tokenSave(fcmDto)
    }
    @DeleteMapping
    fun tokenDelete(fcmDto: FcmDto){
        fcmTokenDeleteService.tokenDelete(fcmDto)
    }
    @GetMapping("/list")
    fun tokenList(@RequestParam("fcm_token") token: String): List<FcmByTokenDto> {
        return fcmTokenRequestService.tokenRequest(token)
    }
}