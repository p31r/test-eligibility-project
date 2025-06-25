package com.example.usereligibilityapp.domain.model

data class Accounts(
    val client: AccountClient,
    val accounts: List<AccountDetail>
)

data class AccountClient(
    val clientId: String,
    val forename: String,
    val surname: String
)

data class AccountDetail(
    val product_id: String,
    val iban: String?,
    val currency: Currency,
    val closing_date: String? = null
)

enum class Currency {
    CZK, USD, EUR, CHF, GBP
}