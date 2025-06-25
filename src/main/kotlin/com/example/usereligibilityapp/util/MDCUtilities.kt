package com.example.usereligibilityapp.util

import org.slf4j.MDC

const val MDC_CLIENT_ID = "clientId"
const val MDC_CORRELATION_ID = "correlation-id"

fun <T> withMdc(pairs: Map<String, String?>, wrappedCall: () -> T): T {
    val oldContext = MDC.getCopyOfContextMap()
    try {
        pairs.forEach { k, v -> v?.let { MDC.put(k, it) } }
        return wrappedCall()
    } finally {
        MDC.setContextMap(oldContext)
    }
}