package handlers

import kotlinx.datetime.Clock
import model.BetterOrioksScreen
import platform.UserNotifications.UNMutableNotificationContent
import platform.UserNotifications.UNNotificationRequest
import platform.UserNotifications.UNTimeIntervalNotificationTrigger
import platform.UserNotifications.UNUserNotificationCenter

class IosNotificationsHandler : NotificationsHandler {

    override fun sendNotification(
        title: String,
        subtitle: String,
        screenOpenAction: BetterOrioksScreen?
    ) {
        val content = UNMutableNotificationContent().apply {
            setTitle(title)
            setBody(subtitle)
        }

        val trigger = UNTimeIntervalNotificationTrigger.triggerWithTimeInterval(1.0, false)
        val request = UNNotificationRequest.requestWithIdentifier(
            "notification_${Clock.System.now().toEpochMilliseconds()}",
            content,
            trigger
        )

        UNUserNotificationCenter.currentNotificationCenter().addNotificationRequest(
            request,
            withCompletionHandler = null
        )
    }
}