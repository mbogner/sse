package dev.mbo.sse.controller.util

import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import java.security.Principal

class SecurityUtil {

    companion object {
        fun requirePrincipalName(principal: Mono<Principal>) : Mono<String> {
            return principal.map(Principal::getName).switchIfEmpty { throw IllegalStateException("user is required") }
        }
    }

}