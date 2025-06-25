package com.example.usereligibilityapp.audit.annotation

import com.example.usereligibilityapp.audit.model.AuditLogType

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Auditable(val type: AuditLogType, val typeMethod: String)
