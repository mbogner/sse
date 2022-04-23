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

package dev.mbo.sse.controller

import org.springframework.http.MediaType
import org.springframework.integration.channel.FluxMessageChannel
import org.springframework.messaging.Message
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
class SseSample(
    private val pub: FluxMessageChannel,
) {

    @GetMapping(path = ["/stream/{id}"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun stream(@PathVariable(name = "id", required = true) id: String): Flux<Message<*>> {
        return Flux.from(pub)
    }

}