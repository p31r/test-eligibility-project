package com.example.usereligibilityapp.service

import com.example.usereligibilityapp.domain.model.EligibilityStatus


interface EligibilityService {
    fun evaluate(clientId: String, correlationId: String?): EligibilityStatus
}