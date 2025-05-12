package ui.settingsScreen

import model.settings.Theme

data class SettingsUiState(
    val selectedTheme: Theme? = null,
    val softenDarkTheme: Boolean = false,
    val pinkMode: Boolean = false,
    val showHiddenSettings: Boolean = false,
    val enableColoredBorders: Boolean = false,
    val enableIosNotifications: Boolean = false,
    val enableForceNotifications: Boolean = true,
    val showDonationWidget: Boolean = true,
    val logAllNotificationActivity: Boolean = false,
) {
    val showSoftenDarkThemeSwitch = selectedTheme != Theme.Light
}
