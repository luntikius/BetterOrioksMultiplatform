package handlers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import data.background.NewsNotificationsBackgroundTask
import data.background.SubjectNotificationsBackgroundTask
import model.background.BackgroundTask
import model.background.BackgroundTaskType
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DefaultWorker(
    context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters), KoinComponent {
    private val taskType =
        BackgroundTaskType.entries.find { it.name == workerParameters.inputData.getString(PARAM_NAME) }

    override suspend fun doWork(): Result {
        val task: Lazy<BackgroundTask> = when (taskType) {
            BackgroundTaskType.NewsNotifications -> inject<NewsNotificationsBackgroundTask>()
            BackgroundTaskType.SubjectNotifications -> inject<SubjectNotificationsBackgroundTask>()
            null -> return Result.failure()
        }
        task.value.execute()
        return Result.success()
    }

    companion object {
        private const val PARAM_NAME = "task_type"
    }
}