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

package dev.mbo.sse.rabbit.c1

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
class Rabbit1ConnectionConfig(
    private val rabbitConfigurer: RabbitConfigurer,
) {
    companion object {
        const val QUALIFIER = "rabbitmq1"
    }

    @Bean
    @Qualifier(QUALIFIER)
    fun rabbitConnectionFactory1(
        @Value("\${spring.rabbitmq1.host}") host: String,
        @Value("\${spring.rabbitmq1.port}") port: Int,
        @Value("\${spring.rabbitmq1.username}") username: String,
        @Value("\${spring.rabbitmq1.password}") password: String,
        @Value("\${spring.rabbitmq1.virtual-host}") virtualHost: String,
        @Value("\${spring.rabbitmq1.publisher-confirm-type}") publisherConfirmType: ConfirmType,
        @Value("\${spring.rabbitmq1.publisher-returns}") publisherReturns: Boolean
    ): CachingConnectionFactory {
        return rabbitConfigurer.createConnectionFactory(
            host, port, username, password, virtualHost, publisherConfirmType, publisherReturns
        )
    }

    @Bean
    @Qualifier(QUALIFIER)
    fun rabbitTemplate1(
        @Qualifier(QUALIFIER) connectionFactory: ConnectionFactory,
        @Value("\${spring.rabbitmq1.template.mandatory}") mandatory: Boolean
    ): RabbitTemplate {
        return rabbitConfigurer.createTemplate(connectionFactory, mandatory)
    }

    @Bean
    @Qualifier(QUALIFIER)
    fun rabbitListenerContainerFactory1(
        @Qualifier(QUALIFIER) connectionFactory: ConnectionFactory,
        @Value("\${spring.rabbitmq1.listener.simple.acknowledge-mode}") acknowledge: String,
        @Value("\${spring.rabbitmq1.listener.simple.prefetch}") prefetch: Int
    ): SimpleRabbitListenerContainerFactory {
        return rabbitConfigurer.createSimpleContainerFactory(connectionFactory, acknowledge, prefetch)
    }

    @Bean
    @Qualifier(QUALIFIER)
    fun rabbitAdmin1(
        @Qualifier(QUALIFIER) connectionFactory: ConnectionFactory
    ): RabbitAdmin {
        return rabbitConfigurer.createRabbitAdmin(connectionFactory)
    }

}