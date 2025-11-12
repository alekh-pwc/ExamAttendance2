package com.example.examattendance.domain.usecase

import androidx.room.Embedded
import androidx.room.Relation
import com.example.examattendance.domain.model.CandidateEntity
import com.example.examattendance.domain.model.ExamEntity

data class ExamWithCandidates(
    @Embedded val exam: ExamEntity,
    @Relation(
        parentColumn = "examId",
        entityColumn = "examId"
    )
    val candidates: List<CandidateEntity>
)
