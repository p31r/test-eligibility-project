package com.example.usereligibilityapp.domain

import com.example.usereligibilityapp.domain.model.AccountDetail
import com.example.usereligibilityapp.domain.model.ClientDetail
import com.example.usereligibilityapp.domain.model.EligibilityReason
import com.example.usereligibilityapp.domain.model.EligibilityStatus
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.Period

@Component
class EligibilityEvaluator {

    fun evaluate(client: ClientDetail?, accounts: List<AccountDetail>?): EligibilityStatus {
        val reasons = mutableSetOf<EligibilityReason>()

        if (client == null || !isAdult(client.birthDate)) {
            reasons.add(EligibilityReason.NO_ADULT)
        }

        if (accounts == null || accounts.isEmpty()) {
            reasons.add(EligibilityReason.NO_ACCOUNT)
        }

        return if (reasons.isEmpty()) {
            EligibilityStatus(eligible = true, reasons = emptySet())
        } else {
            EligibilityStatus(eligible = false, reasons = reasons)
        }
    }

    private fun isAdult(birthDate: LocalDate): Boolean {
        return Period.between(birthDate, LocalDate.now()).years >= 18
    }
}