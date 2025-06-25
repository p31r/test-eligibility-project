package com.example.usereligibilityapp.client.clients

import com.example.usereligibilityapp.audit.annotation.Auditable
import com.example.usereligibilityapp.audit.model.AuditLogType
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

private val logger = KotlinLogging.logger {}

@Component
@Profile("!dev")
class ClientApiClientImpl(
    private val clientsWebClient: WebClient,
    @Value("\${external.api-key}") private val apiKey: String
) : ClientApiClient {

    @Auditable(type = AuditLogType.CLIENT_CALL, "GET clients")
    override fun getClientDetails(
        clientId: String,
        correlationId: String?
    ): ClientResponseDto? = clientsWebClient.get()
        .uri("/$clientId")
        .header("clientId", clientId)
        .header("api-key", apiKey)
        .apply { correlationId?.let { header("correlation-id", it) } }
        .also { logger.info { "Calling Clients API - clientId: $clientId" } }
        .retrieve()
        .bodyToMono(ClientResponseDto::class.java)
        .block()
        ?.also {
            logger.info { "Clients API response retrieved for clientId: $clientId" }
            logger.debug { "Clients API response | clientId: $clientId | correlationId: $correlationId | body: $it" }
        }
}
