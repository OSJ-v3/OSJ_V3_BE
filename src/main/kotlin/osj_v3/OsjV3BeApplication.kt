package osj_v3

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class OsjV3BeApplication

fun main(args: Array<String>) {
    runApplication<OsjV3BeApplication>(*args)
}
