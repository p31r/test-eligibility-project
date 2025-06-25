package com.example.usereligibilityapp.repository

import com.example.usereligibilityapp.entity.EligibilityEvaluationEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EligibilityEvaluationRepository : JpaRepository<EligibilityEvaluationEntity, Long>
