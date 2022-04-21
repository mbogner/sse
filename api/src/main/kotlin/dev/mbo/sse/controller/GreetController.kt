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