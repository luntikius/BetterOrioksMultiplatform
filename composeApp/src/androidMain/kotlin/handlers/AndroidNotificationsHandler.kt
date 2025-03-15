package handlers

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.studentapp.betterorioks.MainActivity
import com.studentapp.betterorioks.R
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import model.BetterOrioksScreen

class AndroidNotificationsHandler(private val context: Context) : NotificationsHandler {

    private val channelId = "orioks_channeldfx"

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Уведомления о событиях ORIOKS",
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.apply {
                createNotificationChannel(channel)
            }
        }
    }

    private fun getPendingIntent(
        screenOpenAction: BetterOrioksScreen?
    ) = screenOpenAction?.let {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(MainActivity.EXTRA_SCREEN, Json.encodeToString(screenOpenAction))
        }
        PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    override fun sendNotification(
        title: String,
        subtitle: String,
        screenOpenAction: BetterOrioksScreen?
    ) {
        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.logo)
            .setContentTitle(title)
            .setContentText(subtitle)
            .setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_ALL)

        getPendingIntent(screenOpenAction).let { notification.setContentIntent(it) }

        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.notify(System.currentTimeMillis().toInt(), notification.build())
    }
}
