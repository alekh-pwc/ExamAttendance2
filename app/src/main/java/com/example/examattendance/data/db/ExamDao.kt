package com.example.examattendance.data.db

import androidx.room.*
import com.example.examattendance.domain.model.CandidateEntity
import com.example.examattendance.domain.model.ExamEntity
import com.example.examattendance.domain.usecase.ExamWithCandidates

import kotlinx.coroutines.flow.Flow

@Dao
interface ExamDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExams(exams: List<ExamEntity>)

    @Query("SELECT * FROM exams")
    fun getAllExams(): Flow<List<ExamEntity>>


    @Query("SELECT * FROM exams WHERE examId = :examId")
    suspend fun getExam(examId: String): ExamEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCandidates(candidates: List<CandidateEntity>)

    @Transaction
    suspend fun upsertExamsAndCandidates(exams: List<ExamEntity>, candidates: List<CandidateEntity>) {
        insertExams(exams)
        insertCandidates(candidates)
    }

    @Query("SELECT * FROM candidates WHERE examId = :examId")
     fun getCandidatesForExam(examId: String): Flow<List<CandidateEntity>>

    @Query("UPDATE candidates SET isPresent = :present WHERE examId = :examId AND admitQr = :qrCode")
    suspend fun markCandidatePresent(examId: String, qrCode: String, present: Boolean = true)

    @Query("SELECT * FROM candidates WHERE examId = :examId AND admitQr = :admitQr LIMIT 1")
    suspend fun getCandidateByAdmitQr(examId: String, admitQr: String): CandidateEntity?

    @Query("SELECT * FROM candidates WHERE examId = :examId AND roll = :roll LIMIT 1")
    suspend fun getCandidateByRoll(examId: String, roll: String): CandidateEntity?

    @Transaction
    @Query("SELECT * FROM exams WHERE examId = :examId")
    suspend fun getExamWithCandidates(examId: String): ExamWithCandidates?
}
