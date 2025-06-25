package com.example.usereligibilityapp.web

data class EligibilityResponseDto(
    val eligible: Boolean,
    val reasons: List<EligibilityReasonDto> = emptyList()
)

enum class EligibilityReasonDto {
    NO_ADULT,
    NO_ACCOUNT
}
