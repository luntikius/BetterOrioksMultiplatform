package ui.controlEventsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.SubjectsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import model.request.ResponseState
import model.resources.ResourcePopupVisibilityState
import model.subjectPerformance.ControlEventsListItem
import model.subjects.SubjectListItem
import model.subjects.SubjectsState

class ControlEventsViewModel(
    private val id: String,
    private val semesterId: String,
    private val subjectsRepository: SubjectsRepository
) : ViewModel() {
    private val subjectsState = subjectsRepository.subjectsState

    private val _controlEventsUiState = MutableStateFlow(ControlEventsUiState())
    val controlEventsUiState = _controlEventsUiState.asStateFlow()

    init {
        viewModelScope.launch {
            subjectsRepository.getSubjects(
                semesterId = semesterId
            )
        }
        collectSubjectsState()
    }

    private fun collectSubjectsState() {
        viewModelScope.launch {
            subjectsState.collect { subjectsState ->
                val displaySubjectPerformanceState = when (subjectsState) {
                    is SubjectsState.NotStarted -> ResponseState.NotStarted
                    is SubjectsState.Loading -> ResponseState.Loading()
                    is SubjectsState.Error -> ResponseState.Error(subjectsState.exception)
                    is SubjectsState.Success -> {
                        val displaySubjectPerformance = subjectsState.displaySubjectPerformance[id]
                        if (displaySubjectPerformance != null) {
                            ResponseState.Success(displaySubjectPerformance)
                        } else {
                            ResponseState.Error(IllegalArgumentException("invalid subject id"))
                        }
                    }
                }
                _controlEventsUiState.update { uis ->
                    uis.copy(displaySubjectPerformanceState = displaySubjectPerformanceState)
                }
            }
        }
    }

    fun reloadSubjects() {
        viewModelScope.launch {
            subjectsRepository.getSubjects(semesterId = semesterId, reload = true)
        }
    }

    fun showInfoPopup(subjectListItem: SubjectListItem) {
        _controlEventsUiState.update { uis ->
            uis.copy(infoPopupVisibility = InfoPopupVisibilityState.Visible(subjectListItem = subjectListItem))
        }
    }

    fun hideInfoPopup() {
        _controlEventsUiState.update { uis ->
            uis.copy(infoPopupVisibility = InfoPopupVisibilityState.Invisible)
        }
    }

    fun showResourcePopup(controlEventItem: ControlEventsListItem.ControlEventItem) {
        _controlEventsUiState.update { uis ->
            uis.copy(
                resourcePopupVisibility = ResourcePopupVisibilityState.Visible(
                    controlEventItem.fullName,
                    controlEventItem.resources
                )
            )
        }
    }

    fun hideResourcePopup() {
        _controlEventsUiState.update { uis ->
            uis.copy(resourcePopupVisibility = ResourcePopupVisibilityState.Invisible)
        }
    }

    fun onRecyclerFirstVisibleItemIndexChanged(index: Int) {
        if (index == 0) {
            _controlEventsUiState.update { uis ->
                uis.copy(shouldShowNameInHeader = false)
            }
        } else {
            _controlEventsUiState.update { uis ->
                uis.copy(shouldShowNameInHeader = true)
            }
        }
    }
}
