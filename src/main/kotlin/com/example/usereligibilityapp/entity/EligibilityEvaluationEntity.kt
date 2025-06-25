package com.example.usereligibilityapp.entity

import com.example.usereligibilityapp.domain.model.EligibilityReason
import com.example.usereligibilityapp.util.EligibilityReasonSetConverter
import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "eligibility_evaluations")
data class EligibilityEvaluationEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val clientId: String,

    @Column(nullable = false)
    val evaluatedAt: Instant = Instant.now(),

    @Column(nullable = false)
    val isEligible: Boolean,

    @Convert(converter = EligibilityReasonSetConverter::class)
    @Column(name = "reasons", nullable = false)
    val reasons: Set<EligibilityReason>
)