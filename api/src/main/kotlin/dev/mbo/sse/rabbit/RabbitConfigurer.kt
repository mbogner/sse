/*
 * Copyright 2022 mbo.dev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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