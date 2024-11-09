package data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import model.subjects.DisplaySubject

class SubjectsRepository(
    private val subjectsWebRepository: SubjectsWebRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) {
    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    suspend fun getSubjects(): List<DisplaySubject> {
        val authData = userPreferencesRepository.authData.first()
        return subjectsWebRepository.getSubjects(authData).getDisplaySubjects()
    }
}