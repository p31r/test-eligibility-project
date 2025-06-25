package com.example.usereligibilityapp.audit.service

import com.example.usereligibilityapp.audit.model.AuditLog

interface AuditLogService {

    fun logRestCall(auditLog: AuditLog)
}