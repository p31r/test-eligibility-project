package com.example.usereligibilityapp.audit.aspect

import com.example.usereligibilityapp.audit.annotation.Auditable
import com.example.usereligibilityapp.audit.model.AuditLog
import com.example.usereligibilityapp.audit.service.AuditLogService
import com.example.usereligibilityapp.util.MDC_CLIENT_ID
import com.example.usereligibilityapp.util.MDC_CORRELATION_ID
import com.example.usereligibilityapp.util.mapper
import io.github.oshai.kotlinlogging.KotlinLogging
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.CodeSignature
import org.slf4j.MDC
import org.springframework.stereotype.Component
import java.util.*

private val logger = KotlinLogging.logger {}

@Aspect
@Component
class AuditAspect(
    private val auditLogService: AuditLogService
) {

    /**
     * AOP advice that intercepts methods annotated with [Auditable] to log audit information.
     *
     * This advice captures request metadata (from method arguments and MDC),
     * invokes the original method, and stores the result (or exception message) along with
     * audit metadata into the audit log storage via [auditLogService].
     *
     * Audit entries include:
     * - `clientId` and `correlationId` from the MDC context.
     * - Method name and type details from the [Auditable] annotation.
     * - Serialized request parameters and response payloads.
     *
     * In case of an exception during method execution, it logs the failure with the error message
     * and rethrows the exception.
     *
     * @param joinPoint The join point representing the method being intercepted.
     * @param auditable The annotation instance containing audit metadata.
     * @return The result of the original method execution.
     * @throws Exception Rethrows any exception thrown by the original method.
     */

    @Around("@annotation(auditable)")
    fun logAudit(joinPoint: ProceedingJoinPoint, auditable: Auditable): Any? {
        logger.debug { "Calling AOP to log audit" }

        val clientId = MDC.get(MDC_CLIENT_ID) ?: let {
            // Not handling error cases with not provided userId
            logger.warn { "userId not present in MDC, generating random number!" }
            UUID.randomUUID().toString()
        }

        val correlationId = MDC.get(MDC_CORRELATION_ID)
        val method = joinPoint.signature.name
        val type = auditable.type
        val typeMethod = auditable.typeMethod

        val requestParameterNames = (joinPoint.signature as CodeSignature).parameterNames
        val requestParameters = joinPoint.args
        val requestParametersMap = requestParameterNames
            .mapIndexed { index, name -> name to requestParameters[index] }
            .toMap()

        val request = mapper.writeValueAsString(requestParametersMap)

        try {
            val result = joinPoint.proceed()

            auditLogService.logRestCall(
                AuditLog(
                    clientId = clientId,
                    correlationId = correlationId,
                    wasSuccessful = true,
                    method = method,
                    type = type,
                    typeMethod = typeMethod,
                    request = request,
                    response = mapper.writeValueAsString(result)
                )
            )

            return result

        } catch (e: Exception) {
            auditLogService.logRestCall(
                AuditLog(
                    clientId = clientId,
                    correlationId = correlationId,
                    wasSuccessful = false,
                    method = method,
                    type = type,
                    typeMethod = typeMethod,
                    request = request,
                    response = e.message ?: "Unknown error"
                )
            )

            throw e
        }
    }
}