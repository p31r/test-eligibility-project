package com.example.usereligibilityapp.domain.mapper

import com.example.usereligibilityapp.client.accounts.AccountClientDto
import com.example.usereligibilityapp.client.accounts.AccountDto
import com.example.usereligibilityapp.client.accounts.AccountsResponseDto
import com.example.usereligibilityapp.client.accounts.CurrencyDto
import com.example.usereligibilityapp.domain.model.AccountClient
import com.example.usereligibilityapp.domain.model.AccountDetail
import com.example.usereligibilityapp.domain.model.Accounts
import com.example.usereligibilityapp.domain.model.Currency

fun AccountsResponseDto.toDomain(): Accounts = Accounts(
    client.toDomain(), accounts.map { it.toDomain() })

fun AccountClientDto.toDomain(): AccountClient = AccountClient(clientId, forename, surname)

fun AccountDto.toDomain(): AccountDetail = AccountDetail(product_id, iban, currency.toDomain(), closing_date)

fun CurrencyDto.toDomain(): Currency = Currency.valueOf(name)