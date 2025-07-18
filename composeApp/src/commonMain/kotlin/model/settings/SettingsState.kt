package model.settings

data class SettingsState(
    val theme: Theme,
    val softenDarkTheme: Boolean,
    val pinkMode: Boolean,
    val coloredBorders: Boolean,
    val enableIosNotifications: Boolean,
    val enableForceNotification: Boolean,
    val showDonationWidget: Boolean,
    val logAllNotificationActivity: Boolean,
)
