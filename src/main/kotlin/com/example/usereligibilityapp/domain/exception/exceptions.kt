package com.example.usereligibilityapp.domain.exception

class BadRequestException(val correlationId: String?) : RuntimeException("Požadavek nelze zpracovat")