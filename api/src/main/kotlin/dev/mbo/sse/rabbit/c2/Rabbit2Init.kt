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

package dev.mbo.sse.rabbit.c2

import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Configuration
import javax.annotation.PostConstruct


@Configuration
class Rabbit2Init(
    @Qualifier(Rabbit2ConnectionConfig.QUALIFIER) private val admin: RabbitAdmin,
    @Qualifier("topicExchange2") private val topicExchange2: TopicExchange,
    @Qualifier("queue2") private val queue2: Queue
) {
    companion object {
        const val ROUTING_KEY2 = "routing.key.example2.new"
    }

    @PostConstruct
    fun init() {
        admin.declareExchange(topicExchange2)
        admin.declareQueue(queue2)
        admin.declareBinding(
            BindingBuilder
                .bind(queue2) //Create Queue Directly
                .to(topicExchange2) //Create switches directly to establish associations
                .with(ROUTING_KEY2)
        ) //Specify Routing Key

    }

}