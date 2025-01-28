package ui.controlEventsScreen

import model.request.ResponseState
import model.subjectPerformance.ControlEventsListItem
import model.subjectPerformance.DisplaySubjectPerformance

data class ControlEventsUiState(
    val displaySubjectPerformanceState: ResponseState<DisplaySubjectPerformance> = ResponseState.NotStarted,
    val shouldShowNameInHeader: Boolean = false,
    val resourcePopupVisibility: ResourcePopupVisibilityState = ResourcePopupVisibilityState.Invisible
)

sealed interface ResourcePopupVisibilityState {

    data object Invisible : ResourcePopupVisibilityState

    data class Visible(
        val controlEventItem: ControlEventsListItem.ControlEventItem
    ) : ResourcePopupVisibilityState
}
