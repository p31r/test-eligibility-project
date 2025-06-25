package com.example.usereligibilityapp.client.accounts

import com.example.usereligibilityapp.audit.annotation.Auditable
import com.example.usereligibilityapp.audit.model.AuditLogType
import com.example.usereligibilityapp.util.mapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
@Profile("dev")
class MockAccountsApiClient(
    @Value("classpath:mock/mock-accounts.json")
    private val mockResource: Resource,
) : AccountsApiClient {

    private val mockJson: Map<String, AccountsResponseDto>

    init {
        logger.info { "Initialized MockAccountsApiClient" }

        mockJson = mapper.readValue(mockResource.inputStream)

        logger.debug { "Mock data: $mockJson" }
    }

    @Auditable(type = AuditLogType.CLIENT_CALL, "GET accounts /list")
    override fun getClientAccounts(clientId: String, correlationId: String?): AccountsResponseDto? = mockJson[clientId]

}