package com.example.examattendance.ui.dashboard

import com.example.examattendance.domain.model.ExamEntity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examattendance.data.repository.ExamsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repo: ExamsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState

    init {
        fetchExams()
    }

    private fun fetchExams() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                repo.syncWithServer()
                val examsFromDb = repo.getAllExams().first()
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    exams = examsFromDb,
                    error = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
}

data class DashboardUiState(
    val isLoading: Boolean = false,
    val exams: List<ExamEntity> = emptyList(),
    val error: String? = null
)
