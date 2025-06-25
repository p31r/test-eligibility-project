package com.example.usereligibilityapp.web

import com.example.usereligibilityapp.audit.annotation.Auditable
import com.example.usereligibilityapp.audit.model.AuditLogType
import com.example.usereligibilityapp.domain.mapper.toDto
import com.example.usereligibilityapp.service.EligibilityService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

const val URL_METHOD_ELIGIBILITY = "/api/v1/eligibility"

@RestController
@RequestMapping(URL_METHOD_ELIGIBILITY)
class EligibilityController(
    private val eligibilityService: EligibilityService
) {

    /**
     * Handles GET requests to check eligibility for a given client.
     *
     * This endpoint evaluates the eligibility of a client based on their `clientId`
     * and an optional `correlation-id`. It returns an [EligibilityResponseDto] with the evaluation result.
     *
     * The request is auditable and an audit log will be persisted with type [AuditLogType.REST_CALL].
     *
     * @param clientId The unique identifier of the client (required).
     * @param correlationId Optional identifier used for request tracing across systems.
     *
     * @return HTTP 200 OK with the eligibility evaluation result.
     */

    @GetMapping
    @Auditable(type = AuditLogType.REST_CALL, "GET $URL_METHOD_ELIGIBILITY")
    fun checkEligibility(
        @RequestHeader("clientId") clientId: String,
        @RequestHeader("correlation-id", required = false) correlationId: String?
    ): ResponseEntity<EligibilityResponseDto> = ResponseEntity.ok(
        eligibilityService.evaluate(clientId, correlationId).toDto()
    )
}