package com.example.usereligibilityapp.client.clients

interface ClientApiClient {
    fun getClientDetails(clientId: String, correlationId: String?): ClientResponseDto?
}
