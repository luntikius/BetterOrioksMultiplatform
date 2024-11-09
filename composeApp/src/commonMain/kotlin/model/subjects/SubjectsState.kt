package model.subjects

import model.schedule.scheduleJson.Semester

sealed interface SubjectsState {
    data object NotStarted : SubjectsState

    data object Loading : SubjectsState

    data class Success(
        val displaySubjects: List<DisplaySubject>,
        val semesters: List<Semester>
    ) : SubjectsState

    data class Error(val message: String) : SubjectsState
}
