package model.resources

sealed interface ResourcePopupVisibilityState {

    data object Invisible : ResourcePopupVisibilityState

    data class Visible(
        val name: String,
        val resources: List<DisplayResource>
    ) : ResourcePopupVisibilityState
}
