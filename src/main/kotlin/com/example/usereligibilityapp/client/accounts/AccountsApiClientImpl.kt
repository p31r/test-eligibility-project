package com.example.usereligibilityapp.client.accounts

import com.example.usereligibilityapp.audit.annotation.Auditable
import com.example.usereligibilityapp.audit.model.AuditLogType
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

private val logger = KotlinLogging.logger {}
private const val URI_METHOD_ACCOUNTS_LIST = "/list"

@Component
@Profile("!dev")
class AccountsApiClientImpl(
    private val accountsWebClient: WebClient,
    @Value("\${external.api-key}") private val apiKey: String
) : AccountsApiClient {

    @Auditable(type = AuditLogType.CLIENT_CALL, "GET accounts /list")
    override fun getClientAccounts(
        clientId: String,
        correlationId: String?
    ): AccountsResponseDto? = accountsWebClient.post()
        .uri(URI_METHOD_ACCOUNTS_LIST)
        .header("clientId", clientId)
        .header("api-key", apiKey)
        .apply { correlationId?.let { header("correlation-id", it) } }
        .bodyValue(mapOf("clientId" to clientId))
        .also { logger.info { "Calling Accounts API /list - clientId: $clientId" } }
        .retrieve()
        .bodyToMono(AccountsResponseDto::class.java)
        .block()
        ?.also {
            logger.info { "Accounts API /list response retrieved - clientId: $clientId" }
            logger.debug { "Accounts API /list response - clientId: $clientId | correlationId: $correlationId | body: $it" }
        }
}
