package dev.mbo.sse.rabbit.c2

import dev.mbo.sse.rabbit.RabbitConfigurer
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory.ConfirmType
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class Rabbit2ConnectionConfig(
    private val rabbitConfigurer: RabbitConfigurer,
) {

    companion object {
        const val QUALIFIER = "rabbitmq2"
    }

    @Bean
    @Qualifier(QUALIFIER)
    fun rabbitConnectionFactory2(
        @Value("\${spring.rabbitmq2.host}") host: String,
        @Value("\${spring.rabbitmq2.port}") port: Int,
        @Value("\${spring.rabbitmq2.username}") username: String,
        @Value("\${spring.rabbitmq2.password}") password: String,
        @Value("\${spring.rabbitmq2.virtual-host}") virtualHost: String,
        @Value("\${spring.rabbitmq2.publisher-confirm-type}") publisherConfirmType: ConfirmType,
        @Value("\${spring.rabbitmq2.publisher-returns}") publisherReturns: Boolean
    ): CachingConnectionFactory {
        return rabbitConfigurer.createConnectionFactory(
            host, port, username, password, virtualHost, publisherConfirmType, publisherReturns
        )
    }

    @Bean
    @Qualifier(QUALIFIER)
    fun rabbitTemplate2(
        @Qualifier(QUALIFIER) connectionFactory: ConnectionFactory,
        @Value("\${spring.rabbitmq2.template.mandatory}") mandatory: Boolean
    ): RabbitTemplate {
        return rabbitConfigurer.createTemplate(connectionFactory, mandatory)
    }

    @Bean
    @Qualifier(QUALIFIER)
    fun rabbitListenerContainerFactory2(
        @Qualifier(QUALIFIER) connectionFactory: ConnectionFactory,
        @Value("\${spring.rabbitmq2.listener.simple.acknowledge-mode}") acknowledge: String,
        @Value("\${spring.rabbitmq2.listener.simple.prefetch}") prefetch: Int
    ): SimpleRabbitListenerContainerFactory {
        return rabbitConfigurer.createSimpleContainerFactory(connectionFactory, acknowledge, prefetch)
    }

    @Bean
    @Qualifier(QUALIFIER)
    fun rabbitAdmin2(
        @Qualifier(QUALIFIER) connectionFactory: ConnectionFactory
    ): RabbitAdmin {
        return rabbitConfigurer.createRabbitAdmin(connectionFactory)
    }

}