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

import dev.mbo.sse.controller.util.SecurityUtil
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.security.Principal

@RestController
class GreetController {

    @GetMapping("/anon/test")
    fun anon(principal: Mono<Principal>): Mono<String> {
        return Mono.just("Hello, anon")
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/admin/test")
    fun admin(principal: Mono<Principal>): Mono<String> {
        return SecurityUtil.requirePrincipalName(principal)
            .map { name -> java.lang.String.format("Hello, %s", name) }
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/user/test")
    fun user(principal: Mono<Principal>): Mono<String> {
        return SecurityUtil.requirePrincipalName(principal)
            .map { name -> java.lang.String.format("Hello, %s", name) }
    }

}