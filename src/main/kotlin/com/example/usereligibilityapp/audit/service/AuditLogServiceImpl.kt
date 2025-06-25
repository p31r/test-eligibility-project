package com.example.usereligibilityapp.audit.service

import com.example.usereligibilityapp.audit.model.AuditLog
import com.example.usereligibilityapp.audit.model.toEntity
import com.example.usereligibilityapp.audit.repository.AuditLogRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class AuditLogServiceImpl(
    private val auditLogRepository: AuditLogRepository
) : AuditLogService {

    override fun logRestCall(auditLog: AuditLog) {
        logger.info { "Logging audit for clientId ${auditLog.clientId} | method: ${auditLog.method}" }
        logger.debug { "Log object: $auditLog" }

        auditLogRepository.save(auditLog.toEntity())
    }

}