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

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.amqp.dsl.Amqp
import org.springframework.integration.channel.FluxMessageChannel
import org.springframework.integration.dsl.IntegrationFlow
import org.springframework.integration.dsl.IntegrationFlows
import org.springframework.integration.handler.LoggingHandler
import java.util.UUID

@Configuration
class Rabbit1Publisher(
    @Qualifier(Rabbit1ConnectionConfig.QUALIFIER) private val rabbitTemplate: RabbitTemplate,
) {

    @Bean
    fun queue1Channel(): FluxMessageChannel {
        return FluxMessageChannel()
    }

    @Bean
    fun queue1Publisher(): IntegrationFlow {
        val from = Amqp
            .inboundAdapter(rabbitTemplate.connectionFactory, "queue1")
            .id("queue1-${UUID.randomUUID()}")
            .get()

        return IntegrationFlows
            .from(from)
            .channel(queue1Channel())
            .log(LoggingHandler.Level.DEBUG)
            .log()
            .get()
    }
}