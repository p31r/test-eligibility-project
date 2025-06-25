package com.example.usereligibilityapp.domain.model

import java.time.LocalDate

data class ClientDetail(
    val clientId: String,
    val forename: String,
    val surname: String,
    val birthDate: LocalDate,
    val gender: Gender,
    val pep: Boolean
)

enum class Gender {
    M, F, NB, O
}
