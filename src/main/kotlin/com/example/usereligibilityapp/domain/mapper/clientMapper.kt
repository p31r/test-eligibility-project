package com.example.usereligibilityapp.domain.mapper

import com.example.usereligibilityapp.client.clients.ClientResponseDto
import com.example.usereligibilityapp.client.clients.GenderDto
import com.example.usereligibilityapp.domain.model.ClientDetail
import com.example.usereligibilityapp.domain.model.Gender

fun ClientResponseDto.toDomain(): ClientDetail =
    ClientDetail(clientId, forename, surname, birthDate, gender.toDomain(), pep)

fun GenderDto.toDomain(): Gender = Gender.valueOf(this.name)