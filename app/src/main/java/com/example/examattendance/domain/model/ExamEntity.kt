package com.example.examattendance.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "exams")
data class ExamEntity(
    @PrimaryKey
    val examId: String,
    val title: String,
    val date: String,
)
