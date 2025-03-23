package ui.subjectsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.SubjectsRepository
import data.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SubjectsViewModel(
    private val subjectsRepository: SubjectsRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    val subjectsState = subjectsRepository.subjectsState
    val isSubjectsGroupingEnabled = userPreferencesRepository.isSubjectsGroupingEnabled

    private val _subjectsScreenUiState = MutableStateFlow(SubjectsScreenUiState())
    val subjectsScreenUiState = _subjectsScreenUiState.asStateFlow()

    fun selectSemester(semesterId: String?) {
        val shouldReload = semesterId != _subjectsScreenUiState.value.selectedSemesterId
        _subjectsScreenUiState.update { uis -> uis.copy(selectedSemesterId = semesterId) }
        getSubjects(shouldReload)
    }

    fun getSubjects(reload: Boolean = false) {
        viewModelScope.launch {
            subjectsRepository.getSubjects(reload, _subjectsScreenUiState.value.selectedSemesterId)
        }
    }

    fun toggleGrouping() {
        viewModelScope.launch {
            userPreferencesRepository.toggleSubjectsGrouping()
        }
    }
}
