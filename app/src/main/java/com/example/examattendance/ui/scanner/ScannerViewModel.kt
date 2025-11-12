package com.example.examattendance.ui.scanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examattendance.data.repository.ExamsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScannerViewModel @Inject constructor(
    private val repo: ExamsRepository,

) : ViewModel() {

    fun markCandidatePresent(examId: String,id: String) {
        viewModelScope.launch {
            repo.markPresence(examId ,id,true )
        }
    }
}
