package com.example.usereligibilityapp.util

import com.example.usereligibilityapp.domain.model.EligibilityReason
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class EligibilityReasonSetConverter : AttributeConverter<Set<EligibilityReason>, String> {
    override fun convertToDatabaseColumn(attribute: Set<EligibilityReason>?): String {
        return attribute?.joinToString(",") ?: ""
    }

    override fun convertToEntityAttribute(dbData: String?): Set<EligibilityReason> {
        return dbData?.split(",")
            ?.mapNotNull { runCatching { EligibilityReason.valueOf(it) }.getOrNull() }
            ?.toSet() ?: emptySet()
    }
}
