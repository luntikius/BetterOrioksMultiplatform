package handlers

import model.BetterOrioksScreen

interface NotificationsHandler {
    fun sendNotification(
        title: String,
        subtitle: String,
        screenOpenAction: BetterOrioksScreen? = null
    )
}
