package data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import model.subjects.SubjectsState

class SubjectsRepository(
    private val subjectsWebRepository: SubjectsWebRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) {
    private val _subjectsState: MutableStateFlow<SubjectsState> = MutableStateFlow(SubjectsState.NotStarted)
    val subjectsState = _subjectsState.asStateFlow()

    suspend fun getSubjects(reload: Boolean = false) {
        if (subjectsState.value is SubjectsState.NotStarted || subjectsState.value is SubjectsState.Error || reload) {
            _subjectsState.update { SubjectsState.Loading }
            try {
                val authData = userPreferencesRepository.authData.first()
                val subjects = subjectsWebRepository.getSubjects(authData)
                _subjectsState.update {
                    SubjectsState.Success(
                        displaySubjects = subjects.displaySubjects
                    )
                }
            } catch (e: Exception) {
                _subjectsState.update { SubjectsState.Error(e.message.toString()) }
            }
        }
    }
}
