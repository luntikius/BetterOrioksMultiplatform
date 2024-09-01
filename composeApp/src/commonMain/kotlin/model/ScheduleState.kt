package model

sealed interface ScheduleState {

    data object Loading : ScheduleState

    data object LoadingFromWeb : ScheduleState

    data class Error(val errorText: String) : ScheduleState

    data object Success : ScheduleState
}