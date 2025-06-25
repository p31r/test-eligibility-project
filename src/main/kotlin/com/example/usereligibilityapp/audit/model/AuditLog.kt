package com.example.usereligibilityapp.audit.model

data class AuditLog(
    val clientId: String,
    val correlationId: String?,
    val wasSuccessful: Boolean,
    val method: String,
    val type: AuditLogType,
    val typeMethod: String,
    val request: String,
    val response: String
)
