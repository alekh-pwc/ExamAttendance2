package com.example.examattendance.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "candidates",
    foreignKeys = [
        ForeignKey(
            entity = ExamEntity::class,
            parentColumns = ["examId"],
            childColumns = ["examId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["examId"])]
)

data class CandidateEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val examId: String,
    val roll: String,
    val name: String,
    val admitQr: String,
    val isPresent: Boolean = false
)
