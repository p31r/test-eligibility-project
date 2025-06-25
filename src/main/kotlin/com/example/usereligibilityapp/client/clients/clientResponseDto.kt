package com.example.usereligibilityapp.client.clients

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.time.LocalDate

@JsonIgnoreProperties(ignoreUnknown = true)
data class ClientResponseDto(
    val clientId: String,
    val forename: String,
    val surname: String,
    val birthDate: LocalDate,
    val gender: GenderDto,
    val pep: Boolean
)

enum class GenderDto {
    M, F, NB, O
}
