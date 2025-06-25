package com.example.usereligibilityapp.web

import com.example.usereligibilityapp.util.MDC_CLIENT_ID
import com.example.usereligibilityapp.util.MDC_CORRELATION_ID
import com.example.usereligibilityapp.util.withMdc
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

/**
 * Servlet filter that populates the [MDC] (Mapped Diagnostic Context) with request-scoped values.
 *
 * Extracts headers like `clientId` and `correlation-id` from the incoming HTTP request and places them
 * into the MDC so they can be included in log messages for traceability and debugging purposes.
 *
 * This filter runs once per request and ensures that the context is correctly set before proceeding
 * down the filter chain.
 *
 * @see withMdc for scoped MDC population.
 */
@Component
class MDCFillerFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val mdcParams = mapOf(
            MDC_CLIENT_ID to request.getHeader("clientId"),
            MDC_CORRELATION_ID to request.getHeader("correlation-id")
        )

        withMdc(mdcParams) {
            filterChain.doFilter(request, response)
        }
    }
}