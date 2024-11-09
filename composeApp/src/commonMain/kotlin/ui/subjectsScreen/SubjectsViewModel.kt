package ui.subjectsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.SubjectsRepository
import data.UserPreferencesRepository
import kotlinx.coroutines.launch

class SubjectsViewModel(
    private val subjectsRepository: SubjectsRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    val subjectsState = subjectsRepository.subjectsState

    fun getSubjects(reload: Boolean = false) {
        viewModelScope.launch {
            subjectsRepository.getSubjects(reload)
        }
    }
}
