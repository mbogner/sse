package dev.mbo.sse.rabbit.c2

import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Rabbit2MQConfig {

    @Bean
    @Qualifier("topicExchange2")
    fun topicExchange2(): TopicExchange {
        return TopicExchange("exchange.topic.example2.new", true, false)
    }

    @Bean
    @Qualifier("queue2")
    fun queue2(): Queue {
        return Queue("queue.example.topic2.new", true)
    }

}