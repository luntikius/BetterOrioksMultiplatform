package model.schedule

sealed interface ScheduleState {

    data object Loading : ScheduleState

    data object LoadingFromWeb : ScheduleState

    data class Error(val exception: Exception) : ScheduleState

    data class Success(val toastState: ToastState = ToastState.NO_TOAST) : ScheduleState
}

enum class ToastState {
    NO_TOAST,
    FAIL_TOAST,
    SUCCESS_TOAST,
}
