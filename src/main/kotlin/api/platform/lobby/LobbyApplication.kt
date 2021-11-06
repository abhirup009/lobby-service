package api.platform.lobby

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableEurekaClient
class LobbyApplication

fun main(args: Array<String>) {
	runApplication<LobbyApplication>(*args)
}
