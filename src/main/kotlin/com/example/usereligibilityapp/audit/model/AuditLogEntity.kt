package com.example.usereligibilityapp.audit.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name = "audit_logs")
data class AuditLogEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val clientId: String,

    @Column
    val correlationId: String?,

    @Column(nullable = false)
    val wasSuccessful: Boolean,

    @Column(nullable = false)
    val method: String,

    @Enumerated(EnumType.STRING)
    val type: AuditLogType,

    @Column(nullable = false)
    val typeMethod: String,

    @Column(nullable = false)
    val createdAt: Instant = Instant.now(),

    @Column(nullable = false)
    val request: String,

    @Column(nullable = false)
    val response: String
)