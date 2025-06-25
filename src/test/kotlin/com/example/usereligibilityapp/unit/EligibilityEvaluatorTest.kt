package com.example.usereligibilityapp.unit

import com.example.usereligibilityapp.domain.EligibilityEvaluator
import com.example.usereligibilityapp.domain.model.AccountDetail
import com.example.usereligibilityapp.domain.model.ClientDetail
import com.example.usereligibilityapp.domain.model.Currency
import com.example.usereligibilityapp.domain.model.EligibilityReason
import com.example.usereligibilityapp.domain.model.Gender
import java.time.LocalDate
import kotlin.test.Test
import kotlin.test.assertTrue

class EligibilityEvaluatorTest {

    private val evaluator = EligibilityEvaluator()

    @Test
    fun `eligible client with account and age above 18`() {
        val client = ClientDetail(
            clientId = "1",
            forename = "client1",
            surname = "client1",
            birthDate = LocalDate.now().minusYears(20),
            gender = Gender.M,
            pep = true
        )
        val accounts = listOf(
            AccountDetail("1", "1", Currency.CZK)
        )

        val result = evaluator.evaluate(client, accounts)

        assertTrue { result.eligible }
        assertTrue { result.reasons.isEmpty() }
    }

    @Test
    fun `ineligible client due to age under 18`() {
        val client = ClientDetail(
            clientId = "1",
            forename = "client1",
            surname = "client1",
            birthDate = LocalDate.now().minusYears(15),
            gender = Gender.M,
            pep = true
        )
        val accounts = listOf(
            AccountDetail("1", "1", Currency.CZK)
        )

        val result = evaluator.evaluate(client, accounts)

        assertTrue { !result.eligible }
        assertTrue { result.reasons.contains(EligibilityReason.NO_ADULT) }
    }

    @Test
    fun `ineligible client due to no account`() {
        val client = ClientDetail(
            clientId = "1",
            forename = "client1",
            surname = "client1",
            birthDate = LocalDate.now().minusYears(20),
            gender = Gender.M,
            pep = true
        )
        val accounts = emptyList<AccountDetail>()

        val result = evaluator.evaluate(client, accounts)

        assertTrue { !result.eligible }
        assertTrue { result.reasons.contains(EligibilityReason.NO_ACCOUNT) }
    }

    @Test
    fun `ineligible client due to age under 18 and no account`() {
        val client = ClientDetail(
            clientId = "1",
            forename = "client1",
            surname = "client1",
            birthDate = LocalDate.now().minusYears(15),
            gender = Gender.M,
            pep = true
        )
        val accounts = emptyList<AccountDetail>()

        val result = evaluator.evaluate(client, accounts)

        assertTrue { !result.eligible }
        assertTrue { result.reasons.contains(EligibilityReason.NO_ACCOUNT) }
        assertTrue { result.reasons.contains(EligibilityReason.NO_ADULT) }
    }

    @Test
    fun `ineligible client due to no client sent`() {
        val client = null
        val accounts = listOf(
            AccountDetail("1", "1", Currency.CZK)
        )

        val result = evaluator.evaluate(client, accounts)

        assertTrue { !result.eligible }
    }

    @Test
    fun `ineligible client due to no account sent`() {
        val client = ClientDetail(
            clientId = "1",
            forename = "client1",
            surname = "client1",
            birthDate = LocalDate.now().minusYears(20),
            gender = Gender.M,
            pep = true
        )
        val accounts = null

        val result = evaluator.evaluate(client, accounts)

        assertTrue { !result.eligible }
    }
}