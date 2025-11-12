package com.example.examattendance.ui.report

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportScreen(
    navController: NavController,
    viewModel: ReportViewModel = hiltViewModel()
) {
    val exams by viewModel.exams.collectAsState()
    val candidates by viewModel.candidates.collectAsState()
    val selectedExamId by viewModel.selectedExamId.collectAsState()

    var expanded by remember { mutableStateOf(false) }
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    val selectedExam = exams.firstOrNull { it.examId == selectedExamId }

    val presentCandidates = candidates.filter { it.isPresent }
    val absentCandidates = candidates.filterNot { it.isPresent }

    val tabs = listOf(
        "Present (${presentCandidates.size})",
        "Absent (${absentCandidates.size})"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                windowInsets = WindowInsets(),
                title = {
                    Text(
                        text = "Attendance Report",
                        style = MaterialTheme.typography.titleMedium
                    )

                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    )

    {

        paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .padding(paddingValues)
        ) {
            // Exam Dropdown
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                TextField(
                    readOnly = true,
                    value = selectedExam?.title ?: "Select Exam",
                    onValueChange = {},
                    label = { Text("Exam") },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    exams.forEach { exam ->
                        DropdownMenuItem(
                            text = { Text(exam.title) },
                            onClick = {
                                viewModel.selectExam(exam.examId)
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (selectedExam != null) {
                Text(
                    text = "Report for: ${selectedExam.title}",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                TabRow(selectedTabIndex = selectedTabIndex) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index },
                            text = { Text(title) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                val displayedList =
                    if (selectedTabIndex == 0) presentCandidates else absentCandidates

                if (displayedList.isEmpty()) {
                    Text(
                        text = if (selectedTabIndex == 0)
                            "No present candidates."
                        else
                            "No absent candidates.",
                        modifier = Modifier.padding(8.dp)
                    )
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(displayedList) { candidate ->
                            Column(modifier = Modifier.padding(vertical = 4.dp)) {
                                Text(
                                    text = candidate.name,
                                    color = viewModel.attendanceStatusColor(candidate.isPresent)
                                )
                                HorizontalDivider(modifier = Modifier.padding(top = 2.dp))
                            }
                        }
                    }
                }
            } else {
                Text("Please select an exam to view report.")
            }
        }
    }
}
