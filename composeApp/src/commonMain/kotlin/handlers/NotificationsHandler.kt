package handlers

interface NotificationsHandler {
    fun sendNotification(title: String, subtitle: String)
}
