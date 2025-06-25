package com.example.usereligibilityapp.audit.model

fun AuditLog.toEntity() = AuditLogEntity(
    clientId = clientId,
    correlationId = correlationId,
    wasSuccessful = wasSuccessful,
    method = method,
    type = type,
    typeMethod = typeMethod,
    request = request,
    response = response
)