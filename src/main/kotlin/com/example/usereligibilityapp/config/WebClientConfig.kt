package com.example.usereligibilityapp.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig {

    @Bean
    fun clientsWebClient(
        @Value("\${external.clients.base-url}") baseUrl: String
    ): WebClient = WebClient.builder().baseUrl(baseUrl).build()

    @Bean
    fun accountsWebClient(
        @Value("\${external.accounts.base-url}") baseUrl: String
    ): WebClient = WebClient.builder().baseUrl(baseUrl).build()
}
