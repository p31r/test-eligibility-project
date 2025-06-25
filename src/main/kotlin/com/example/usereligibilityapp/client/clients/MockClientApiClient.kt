package com.example.usereligibilityapp.client.clients

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
class MockClientApiClient(
    @Value("classpath:mock/mock-clients.json")
    private val mockResource: Resource,
) : ClientApiClient {

    private val mockJson: Map<String, ClientResponseDto>

    init {
        logger.info { "Initialized MockClientApiClient" }

        mockJson = mapper.readValue(mockResource.inputStream)

        logger.debug { "Mock data: $mockJson" }
    }

    @Auditable(type = AuditLogType.CLIENT_CALL, "GET clients")
    override fun getClientDetails(clientId: String, correlationId: String?): ClientResponseDto? = mockJson[clientId]
}