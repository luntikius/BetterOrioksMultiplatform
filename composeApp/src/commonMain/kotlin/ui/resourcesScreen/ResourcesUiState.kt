package ui.resourcesScreen

import model.request.ResponseState
import model.resources.DisplayResourceCategory
import model.resources.ResourcePopupVisibilityState

data class ResourcesUiState(
    val resourcesState: ResponseState<List<DisplayResourceCategory>> = ResponseState.NotStarted,
    val resourcePopupVisibility: ResourcePopupVisibilityState = ResourcePopupVisibilityState.Invisible,
)
