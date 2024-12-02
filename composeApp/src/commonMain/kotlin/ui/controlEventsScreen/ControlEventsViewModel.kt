package ui.controlEventsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.SubjectsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import model.request.ResponseState
import model.subjects.SubjectsState

class ControlEventsViewModel(
    private val id: String,
    private val subjectsRepository: SubjectsRepository
) : ViewModel() {
    private val subjectsState = subjectsRepository.subjectsState

    private val _controlEventsUiState = MutableStateFlow(ControlEventsUiState())
    val controlEventsUiState = _controlEventsUiState.asStateFlow()

    init {
        viewModelScope.launch {
            subjectsRepository.getSubjects()
        }
        collectSubjectsState()
    }

    private fun collectSubjectsState() {
        viewModelScope.launch {
            subjectsState.collect { subjectsState ->
                val displaySubjectPerformanceState = when (subjectsState) {
                    is SubjectsState.NotStarted -> ResponseState.NotStarted
                    is SubjectsState.Loading -> ResponseState.Loading()
                    is SubjectsState.Error -> ResponseState.Error(subjectsState.message)
                    is SubjectsState.Success -> {
                        val displaySubjectPerformance = subjectsState.displaySubjectPerformance[id]
                        if (displaySubjectPerformance != null) {
                            ResponseState.Success(displaySubjectPerformance)
                        } else {
                            ResponseState.Error("invalid subject id")
                        }
                    }
                }
                _controlEventsUiState.update { uis -> uis.copy(displaySubjectPerformanceState) }
            }
        }
    }

    fun reloadSubjects() {
        viewModelScope.launch {
            subjectsRepository.getSubjects(reload = true)
        }
    }
}