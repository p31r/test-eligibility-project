package com.example.usereligibilityapp.service

import com.example.usereligibilityapp.client.accounts.AccountsApiClient
import com.example.usereligibilityapp.client.clients.ClientApiClient
import com.example.usereligibilityapp.domain.EligibilityEvaluator
import com.example.usereligibilityapp.domain.exception.BadRequestException
import com.example.usereligibilityapp.domain.mapper.toDomain
import com.example.usereligibilityapp.domain.mapper.toEntity
import com.example.usereligibilityapp.domain.model.EligibilityStatus
import com.example.usereligibilityapp.repository.EligibilityEvaluationRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class EligibilityServiceImpl(
    private val clientApi: ClientApiClient,
    private val accountApi: AccountsApiClient,
    private val evaluator: EligibilityEvaluator,
    private val eligibilityRepository: EligibilityEvaluationRepository,
) : EligibilityService {

    override fun evaluate(clientId: String, correlationId: String?): EligibilityStatus = try {
        logger.info { "Doing evaluation . clientId: $clientId" }
        evaluateInternal(clientId, correlationId)
    } catch (_: Exception) {
        throw BadRequestException(correlationId)
    }

    private fun evaluateInternal(clientId: String, correlationId: String?): EligibilityStatus {
        val clientObj = getClientDomain(clientId, correlationId)
        val clientAccountsObj = getAccountsDomain(clientId, correlationId)

        val eligibilityStatus = evaluator.evaluate(clientObj, clientAccountsObj?.accounts)
        saveEligibilityEvaluation(clientId, eligibilityStatus)

        logger.info { "Eligibility evaluation done - clientId $clientId | eligible: ${eligibilityStatus.eligible}" }
        logger.debug { "clientId: $clientId | eligibilityStatus: $eligibilityStatus" }

        return eligibilityStatus
    }

    private fun getClientDomain(clientId: String, correlationId: String?) =
        clientApi.getClientDetails(clientId, correlationId)?.toDomain()

    private fun getAccountsDomain(clientId: String, correlationId: String?) =
        accountApi.getClientAccounts(clientId, correlationId)?.toDomain()

    private fun saveEligibilityEvaluation(clientId: String, eligibilityStatus: EligibilityStatus) {
        eligibilityRepository.save(eligibilityStatus.toEntity(clientId))
    }
}