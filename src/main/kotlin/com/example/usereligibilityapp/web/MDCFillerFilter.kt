package com.example.usereligibilityapp.web

import com.example.usereligibilityapp.util.MDC_CLIENT_ID
import com.example.usereligibilityapp.util.MDC_CORRELATION_ID
import com.example.usereligibilityapp.util.withMdc
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

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