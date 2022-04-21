package dev.mbo.sse.rabbit.c1

import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Configuration
import javax.annotation.PostConstruct


@Configuration
class Rabbit1Init(
    @Qualifier(Rabbit1ConnectionConfig.QUALIFIER) private val admin: RabbitAdmin,
    @Qualifier("topicExchange1") private val topicExchange1: TopicExchange,
    @Qualifier("queue1") private val queue1: Queue
) {
    companion object {
        const val ROUTING_KEY1 = "routing.key.example.new"
    }

    @PostConstruct
    fun init() {
        admin.declareExchange(topicExchange1)
        admin.declareQueue(queue1)
        admin.declareBinding(
            BindingBuilder
                .bind(queue1) //Create Queue Directly
                .to(topicExchange1) //Create switches directly to establish associations
                .with(ROUTING_KEY1)
        ) //Specify Routing Key

    }

}