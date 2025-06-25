package com.example.usereligibilityapp.audit.annotation

import com.example.usereligibilityapp.audit.model.AuditLogType

/**
 * Annotation used to mark controller or service methods for audit logging.
 *
 * When applied, the annotated method will trigger the creation of an audit log entry
 * containing metadata such as the type of the action and the method identifier.
 * This is typically handled by an aspect or interceptor.
 *
 * @property type The category of the audit log, typically indicating the kind of operation (e.g. REST call, API call).
 * @property typeMethod A string identifier representing the specific method or action being logged.
 *
 * @see AuditLogType for predefined audit categories.
 */

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Auditable(val type: AuditLogType, val typeMethod: String)
