package com.example.examattendance.data.api

import retrofit2.http.GET

interface ExamsApi {
    @GET("exams.json")
    suspend fun getExams(): List<ExamDto>
}
