package ui.controlEventsScreen

import model.request.ResponseState
import model.subjectPerformance.DisplaySubjectPerformance

data class ControlEventsUiState(
    val displaySubjectPerformanceState: ResponseState<DisplaySubjectPerformance> = ResponseState.NotStarted,
    val shouldShowNameInHeader: Boolean = false
)
