package com.example.usereligibilityapp.web

import com.example.usereligibilityapp.domain.exception.BadRequestException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.*

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequest(e: BadRequestException) = buildBadRequestResponse(e.message, e.correlationId)

    @ExceptionHandler(Exception::class)
    fun handleOther(e: Exception) = buildBadRequestResponse(e.message)

    private fun buildBadRequestResponse(
        message: String?,
        correlationId: String? = null
    ): ResponseEntity<Any> = ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .header("correlation-id", correlationId ?: UUID.randomUUID().toString())
        .body(message)
}