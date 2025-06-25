package com.example.usereligibilityapp.domain.mapper

import com.example.usereligibilityapp.web.EligibilityReasonDto
import com.example.usereligibilityapp.web.EligibilityResponseDto
import com.example.usereligibilityapp.domain.model.EligibilityReason
import com.example.usereligibilityapp.domain.model.EligibilityStatus
import com.example.usereligibilityapp.entity.EligibilityEvaluationEntity

fun EligibilityStatus.toEntity(clientId: String): EligibilityEvaluationEntity = EligibilityEvaluationEntity(
    clientId = clientId,
    isEligible = eligible,
    reasons = reasons
)

fun EligibilityStatus.toDto(): EligibilityResponseDto = EligibilityResponseDto(eligible, reasons.map { it.toDto() })

fun EligibilityReason.toDto(): EligibilityReasonDto = EligibilityReasonDto.valueOf(name)