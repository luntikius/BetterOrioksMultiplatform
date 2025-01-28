package ui.controlEventsScreen

import model.request.ResponseState
import model.resources.ResourcePopupVisibilityState
import model.subjectPerformance.DisplaySubjectPerformance
import model.subjects.SubjectListItem

data class ControlEventsUiState(
    val displaySubjectPerformanceState: ResponseState<DisplaySubjectPerformance> = ResponseState.NotStarted,
    val shouldShowNameInHeader: Boolean = false,
    val resourcePopupVisibility: ResourcePopupVisibilityState = ResourcePopupVisibilityState.Invisible,
    val infoPopupVisibility: InfoPopupVisibilityState = InfoPopupVisibilityState.Invisible
)

sealed interface InfoPopupVisibilityState {
    data object Invisible : InfoPopupVisibilityState

    data class Visible(
        val subjectListItem: SubjectListItem
    ) : InfoPopupVisibilityState
}
