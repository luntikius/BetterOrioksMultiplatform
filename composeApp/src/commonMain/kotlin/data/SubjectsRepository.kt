package data

import data.background.SubjectNotificationsBackgroundTask
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import model.subjects.SubjectsState

class SubjectsRepository(
    private val subjectsWebRepository: SubjectsWebRepository,
    private val userPreferencesRepository: UserPreferencesRepository,
    private val subjectNotificationsBackgroundTask: SubjectNotificationsBackgroundTask
) {
    private var semesterId: String? = null

    private val _subjectsState: MutableStateFlow<SubjectsState> = MutableStateFlow(SubjectsState.NotStarted)
    val subjectsState = _subjectsState.asStateFlow()

    suspend fun getSubjects(reload: Boolean = false, semesterId: String? = null) {
        this.semesterId = semesterId
        if (subjectsState.value is SubjectsState.NotStarted || subjectsState.value is SubjectsState.Error || reload) {
            _subjectsState.update { SubjectsState.Loading }
            try {
                val authData = userPreferencesRepository.authData.first()
                val subjects = subjectsWebRepository.getSubjects(authData, this.semesterId)
                _subjectsState.update {
                    SubjectsState.Success(
                        subjectListItems = subjects.subjectListItems,
                        debtSubjectListItems = subjects.debtSubjectListItems,
                        displaySubjectPerformance = subjects.getDisplaySubjectPerformance(
                            shouldAddWeeksLeftItem = semesterId == null
                        ),
                        semesters = subjects.semesters
                    )
                }
                runCatching {
                    subjectNotificationsBackgroundTask.executeWithData(
                        subjects = subjects,
                        silently = !userPreferencesRepository.isForceNotificationEnabled() ||
                            !userPreferencesRepository.notificationSettings.first().isSubjectNotificationEnabled
                    )
                }
            } catch (e: Exception) {
                _subjectsState.update { SubjectsState.Error(e) }
            }
        }
    }
}
