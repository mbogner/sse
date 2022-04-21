package dev.mbo.sse.rabbit

import dev.mbo.sse.config.LocaleConfig
import org.springframework.amqp.core.AcknowledgeMode
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

@Component
class RabbitConfigurer {
    fun createRabbitAdmin(connectionFactory: ConnectionFactory): RabbitAdmin {
        val rabbitAdmin = RabbitAdmin(connectionFactory)
        rabbitAdmin.isAutoStartup = true
        return rabbitAdmin
    }

    fun createSimpleContainerFactory(
        connectionFactory: ConnectionFactory,
        acknowledge: String,
        prefetch: Int,
    ): SimpleRabbitListenerContainerFactory {
        val factory = SimpleRabbitListenerContainerFactory()
        factory.setConnectionFactory(connectionFactory)
        factory.setAcknowledgeMode(AcknowledgeMode.valueOf(acknowledge.uppercase(LocaleConfig.DEFAULT_LOCALE)))
        factory.setPrefetchCount(prefetch)
        return factory
    }

    fun createTemplate(
        connectionFactory: ConnectionFactory,
        mandatory: Boolean,
    ): RabbitTemplate {
        val template = RabbitTemplate(connectionFactory)
        template.setMandatory(mandatory)
        return template
    }

    fun createConnectionFactory(
        host: String,
        port: Int,
        username: String,
        password: String,
        virtualHost: String,
        publisherConfirmType: CachingConnectionFactory.ConfirmType,
        publisherReturns: Boolean,
    ): CachingConnectionFactory {
        val connectionFactory = CachingConnectionFactory()
        connectionFactory.host = host
        connectionFactory.port = port
        connectionFactory.username = username
        connectionFactory.setPassword(password)
        connectionFactory.virtualHost = virtualHost
        connectionFactory.setPublisherConfirmType(publisherConfirmType)
        connectionFactory.isPublisherReturns = publisherReturns
        return connectionFactory
    }
}