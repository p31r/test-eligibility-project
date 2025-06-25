package com.example.usereligibilityapp.audit.repository

import com.example.usereligibilityapp.audit.model.AuditLogEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuditLogRepository : JpaRepository<AuditLogEntity, Long>