package com.example.examattendance.data.repository

import com.example.examattendance.data.api.CandidateDto
import com.example.examattendance.data.api.ExamDto
import com.example.examattendance.domain.model.CandidateEntity
import com.example.examattendance.domain.model.ExamEntity
import com.example.examattendance.data.api.ExamsApi
import com.example.examattendance.data.db.AppDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExamsRepository @Inject constructor(
    private val api: ExamsApi,
    private val db: AppDatabase
) {
    private val dao = db.examDao()

    suspend fun fetchExamsFromApi(): List<ExamDto> = api.getExams()

    fun getAllExams() = dao.getAllExams()

    fun getCandidates(examId: String): Flow<List<CandidateEntity>> = dao.getCandidatesForExam(examId)

    suspend fun markPresence(examId: String, admitQr: String, isPresent: Boolean) {
        dao.markCandidatePresent(examId, admitQr, isPresent)
    }

    suspend fun insertExams(exams: List<ExamEntity>) {
        dao.insertExams(exams)
    }

    /**
     * Synchronize exams+ candidates from server into local DB.
     * Merge strategy:
     * - Insert/replace exams
     * - For each candidate: if candidate exists locally (matched by admitQr + examId) preserve its id and isPresent flag, but update other fields.
     * - If candidate doesn't exist create a new entry with isPresent=false
     */
    suspend fun syncWithServer() {
        val examDtos = api.getExams()

        // Map and insert exams (no isActive stored in DB)
        val exams = examDtos.map { dto ->
            ExamEntity(dto.examId, dto.title, dto.date)
        }

        val candidatesToInsert = mutableListOf<CandidateEntity>()

        for (exam in examDtos) {
            for (cand in exam.candidates) {
                val existing = dao.getCandidateByAdmitQr(exam.examId, cand.admitQr)
                if (existing != null) {
                    candidatesToInsert.add(
                        existing.copy(
                            examId = exam.examId,
                            roll = cand.roll,
                            name = cand.name,
                            admitQr = cand.admitQr
                        )
                    )
                } else {
                    candidatesToInsert.add(
                        CandidateEntity(
                            examId = exam.examId,
                            roll = cand.roll,
                            name = cand.name,
                            admitQr = cand.admitQr,
                            isPresent = false
                        )
                    )
                }
            }
        }


        dao.upsertExamsAndCandidates(exams, candidatesToInsert)
    }
}
