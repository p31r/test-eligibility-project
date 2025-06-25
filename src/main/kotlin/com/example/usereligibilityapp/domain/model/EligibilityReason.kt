package com.example.usereligibilityapp.domain.model

data class EligibilityStatus(
    val eligible: Boolean,
    val reasons: Set<EligibilityReason>
)

enum class EligibilityReason { NO_ADULT, NO_ACCOUNT}