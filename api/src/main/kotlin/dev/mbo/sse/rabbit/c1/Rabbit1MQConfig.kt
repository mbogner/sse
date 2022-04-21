package dev.mbo.sse.rabbit.c1

import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Rabbit1MQConfig {

    @Bean
    @Qualifier("topicExchange1")
    fun topicExchange1(): TopicExchange {
        return TopicExchange("exchange.topic.example.new", true, false)
    }

    @Bean
    @Qualifier("queue1")
    fun queue1(): Queue {
        return Queue("queue.example.topic.new", true)
    }

}