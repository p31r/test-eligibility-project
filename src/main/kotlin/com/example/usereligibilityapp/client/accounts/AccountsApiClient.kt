package com.example.usereligibilityapp.client.accounts

interface AccountsApiClient {
    fun getClientAccounts(clientId: String, correlationId: String?): AccountsResponseDto?
}