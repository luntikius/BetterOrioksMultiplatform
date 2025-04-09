package ui.menuScreen

import model.user.UserInfoState

data class MenuScreenUiState(
    val userInfoState: UserInfoState = UserInfoState.Loading,
    val iosNotificationsEnabled: Boolean = false,
)
