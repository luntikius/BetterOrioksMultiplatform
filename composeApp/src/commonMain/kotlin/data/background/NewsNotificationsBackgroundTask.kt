package data.background

import betterorioks.composeapp.generated.resources.Res
import betterorioks.composeapp.generated.resources.notification_news_title
import data.NotificationsDatabaseRepository
import data.OrioksWebRepository
import data.UserPreferencesRepository
import handlers.NotificationsHandler
import kotlinx.coroutines.flow.first
import model.background.BackgroundTask
import org.jetbrains.compose.resources.getString

class NewsNotificationsBackgroundTask(
    private val notificationsRepository: NotificationsDatabaseRepository,
    private val orioksWebRepository: OrioksWebRepository,
    private val userPreferencesRepository: UserPreferencesRepository,
    private val notificationsHandler: NotificationsHandler,
) : BackgroundTask {

    override suspend fun execute(silently: Boolean) {
        val authData = userPreferencesRepository.authData.first()
        val news = orioksWebRepository.getNews(authData, OrioksWebRepository.NewsType.Main, null)
        val lastNews = news.firstOrNull() ?: return
        val diff = notificationsRepository.updateNewsAndGetDiff(lastNews.id)
        if (diff) {
            val title = getString(Res.string.notification_news_title)
            val subtitle = lastNews.title
            notificationsHandler.sendNotification(title, subtitle)
            notificationsRepository.addNotification(title, subtitle)
        }
    }

}