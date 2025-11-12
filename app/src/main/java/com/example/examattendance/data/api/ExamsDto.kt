package com.example.examattendance.data.api

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ExamDto(
    val examId: String,
    val title: String,
    val date: String,
    val candidates: List<CandidateDto> = emptyList()
)

@JsonClass(generateAdapter = true)
data class CandidateDto(
    val roll: String,
    val name: String,
    val admitQr: String
)
