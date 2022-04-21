package dev.mbo.sse

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.web.reactive.config.EnableWebFlux

@EnableWebFlux
@SpringBootApplication(
    exclude = [
        RabbitAutoConfiguration::class,
    ]
)
class SseApplication

fun main(args: Array<String>) {
    runApplication<SseApplication>(*args)
}
