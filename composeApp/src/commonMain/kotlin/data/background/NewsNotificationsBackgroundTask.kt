package data.background

import betterorioks.composeapp.generated.resources.Res
import betterorioks.composeapp.generated.resources.notification_news_title
import data.NotificationsDatabaseRepository
import data.OrioksWebRepository
import data.UserPreferencesRepository
import handlers.NotificationsHandler
import kotlinx.coroutines.flow.first
import model.BetterOrioksScreen
import model.background.BackgroundTask
import model.news.News
import org.jetbrains.compose.resources.getString

class NewsNotificationsBackgroundTask(
    private val notificationsRepository: NotificationsDatabaseRepository,
    private val orioksWebRepository: OrioksWebRepository,
    private val userPreferencesRepository: UserPreferencesRepository,
    private val notificationsHandler: NotificationsHandler,
) : BackgroundTask {

    suspend fun executeWithData(news: List<News>, silently: Boolean) {
        val lastNews = news.firstOrNull() ?: return
        val diff = notificationsRepository.updateNewsAndGetDiff(lastNews.id)
        if (diff) {
            val title = getString(Res.string.notification_news_title)
            val subtitle = lastNews.title
            if (!silently) {
                notificationsHandler.sendNotification(
                    title = title,
                    subtitle = subtitle,
                    screenOpenAction = BetterOrioksScreen.NewsViewScreen(id = lastNews.id, type = "Main")
                )
            }
            notificationsRepository.addNotification(title, subtitle)
        }
        if (userPreferencesRepository.settings.first().logAllNotificationActivity) {
            notificationsRepository.addNotification(
                title = "NewsNotificationBackgroudTask",
                text = "task found diff: $diff"
            )
        }
    }

    override suspend fun execute() {
        val authData = userPreferencesRepository.authData.first()
        val news = orioksWebRepository.getNews(authData, OrioksWebRepository.NewsType.Main, null)
        executeWithData(news, false)
    }
}
