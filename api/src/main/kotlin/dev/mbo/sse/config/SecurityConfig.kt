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

package dev.mbo.sse.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@Configuration
class SecurityConfig {

    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        // @formatter:off
        return http.cors().disable()
            .csrf().disable() // stateless rest api has no place for csrf
            .headers().frameOptions().disable()

            .and()
            .authorizeExchange()
            .pathMatchers("/**").permitAll()
            .anyExchange().authenticated()

            .and()
            .httpBasic()
            .and()
            .formLogin().disable()

            .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
            .build()
        // @formatter:on
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun userDetailsService(passwordEncoder: PasswordEncoder): MapReactiveUserDetailsService {
        val admin: UserDetails = User
            .withUsername("admin")
            .password(passwordEncoder.encode("password"))
            .roles("ADMIN")
            .build()
        val user1: UserDetails = User
            .withUsername("user1")
            .password(passwordEncoder.encode("password"))
            .roles("USER")
            .build()
        val user2: UserDetails = User
            .withUsername("user2")
            .password(passwordEncoder.encode("password"))
            .roles("USER")
            .build()
        val user3: UserDetails = User
            .withUsername("user3")
            .password(passwordEncoder.encode("password"))
            .roles("USER")
            .build()
        return MapReactiveUserDetailsService(admin, user1, user2, user3)
    }

}