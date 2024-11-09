package model.subjects


sealed interface SubjectsState {
    data object NotStarted : SubjectsState

    data object Loading : SubjectsState

    data class Success(
        val displaySubjects: List<DisplaySubject>
    ) : SubjectsState

    data class Error(val message: String) : SubjectsState
}
