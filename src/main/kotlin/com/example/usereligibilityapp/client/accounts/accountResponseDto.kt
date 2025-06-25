package com.example.usereligibilityapp.client.accounts

data class AccountsResponseDto(
    val client: AccountClientDto,
    val accounts: List<AccountDto>
)

data class AccountClientDto(
    val clientId: String,
    val forename: String,
    val surname: String
)

data class AccountDto(
    val product_id: String,
    val iban: String?,
    val currency: CurrencyDto,
    val closing_date: String? = null
)

enum class CurrencyDto {
    CZK, USD, EUR, CHF, GBP
}
