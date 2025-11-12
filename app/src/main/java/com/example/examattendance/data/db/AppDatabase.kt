package com.example.examattendance.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.examattendance.domain.model.CandidateEntity
import com.example.examattendance.domain.model.ExamEntity

@Database(entities = [ExamEntity::class, CandidateEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun examDao(): ExamDao

}
