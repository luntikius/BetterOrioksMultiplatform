package ui.subjectsScreen

import androidx.lifecycle.ViewModel
import data.SubjectsRepository
import data.UserPreferencesRepository
import model.subjects.DisplaySubject

class SubjectsViewModel(
    private val subjectsRepository: SubjectsRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {
    suspend fun getSubjects(): List<DisplaySubject> {
        return subjectsRepository.getSubjects()
    }
}
