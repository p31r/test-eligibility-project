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

    @GetMapping
    @Auditable(type = AuditLogType.REST_CALL, "GET $URL_METHOD_ELIGIBILITY")
    fun checkEligibility(
        @RequestHeader("clientId") clientId: String,
        @RequestHeader("correlation-id", required = false) correlationId: String?
    ): ResponseEntity<EligibilityResponseDto> = ResponseEntity.ok(
        eligibilityService.evaluate(clientId, correlationId).toDto()
    )
}