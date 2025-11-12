package com.example.examattendance.ui.report

import androidx.compose.ui.graphics.Color
import com.example.examattendance.domain.model.ExamEntity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examattendance.data.repository.ExamsRepository
import com.example.examattendance.domain.model.CandidateEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val repo: ExamsRepository
) : ViewModel() {

    val exams: StateFlow<List<ExamEntity>> = repo.getAllExams()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _selectedExamId = MutableStateFlow<String?>(null)
    val selectedExamId: StateFlow<String?> = _selectedExamId

    @OptIn(ExperimentalCoroutinesApi::class)
    val candidates: StateFlow<List<CandidateEntity>> = _selectedExamId
        .filterNotNull()
        .flatMapLatest { examId ->
            repo.getCandidates(examId)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun selectExam(examId: String) {
        viewModelScope.launch {
            _selectedExamId.value = examId
        }
    }

    fun attendanceStatusColor(isPresent: Boolean): Color{
        return if(isPresent){
            Color(0xFF018F06)
        } else{
           Color.Red
        }
    }

}
