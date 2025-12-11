package osj_v3.domain.device.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import osj_v3.domain.common.enums.DeviceLocation
import osj_v3.domain.device.dto.DeviceDto
import osj_v3.domain.device.service.DeviceFindByLocationService

@RestController
@RequestMapping("/device")
class DeviceController(
    private val deviceFindByLocationService: DeviceFindByLocationService
) {
    @GetMapping("/{deviceLocation}")
    fun getDevicesByLocation(@PathVariable deviceLocation: DeviceLocation): List<DeviceDto> {
        return deviceFindByLocationService.findDevices(deviceLocation)
    }
}