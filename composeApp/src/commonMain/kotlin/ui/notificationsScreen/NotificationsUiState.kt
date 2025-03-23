package ui.notificationsScreen

import model.NotificationSettings

data class NotificationsUiState(
    val notificationSettings: NotificationSettings = NotificationSettings(false, false),
    val isInfoPopupVisible: Boolean = false
)
