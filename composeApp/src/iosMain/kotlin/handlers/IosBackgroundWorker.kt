package handlers

import data.background.NewsNotificationsBackgroundTask
import data.background.SubjectNotificationsBackgroundTask
import kotlinx.coroutines.runBlocking
import model.background.BackgroundTaskType
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class IosBackgroundWorker : KoinComponent {
    private val backgroundHandler: Lazy<BackgroundHandler> = inject<BackgroundHandler>()

    fun executeTask(taskType: BackgroundTaskType) = runBlocking {
        when (taskType) {
            BackgroundTaskType.NewsNotifications ->
                inject<NewsNotificationsBackgroundTask>().value.execute()
            BackgroundTaskType.SubjectNotifications ->
                inject<SubjectNotificationsBackgroundTask>().value.execute()
        }
        backgroundHandler.value.scheduleTask(backgroundTask = taskType)
    }
}
