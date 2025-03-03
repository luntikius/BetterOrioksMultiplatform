package model.schedule

sealed interface ScheduleState {

    data object Loading : ScheduleState

    data object LoadingFromWeb : ScheduleState

    data class Error(val exception: Exception) : ScheduleState

    data object Success : ScheduleState
}
