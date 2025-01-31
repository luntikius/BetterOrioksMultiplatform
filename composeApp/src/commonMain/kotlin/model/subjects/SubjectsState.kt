package model.subjects

import model.schedule.scheduleJson.Semester
import model.subjectPerformance.DisplaySubjectPerformance

sealed interface SubjectsState {
    data object NotStarted : SubjectsState

    data object Loading : SubjectsState

    data class Success(
        val subjectListItems: List<SubjectListItem>,
        val debtSubjectListItems: List<SubjectListItem>,
        val displaySubjectPerformance: Map<String, DisplaySubjectPerformance>,
        val semesters: List<Semester>
    ) : SubjectsState

    data class Error(val exception: Exception) : SubjectsState
}
