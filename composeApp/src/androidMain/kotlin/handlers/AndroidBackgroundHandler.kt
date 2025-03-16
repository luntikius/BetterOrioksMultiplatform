package handlers

import android.content.Context
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import data.background.NewsNotificationsBackgroundTask
import data.background.SubjectNotificationsBackgroundTask
import model.background.BackgroundTask
import model.background.BackgroundTaskType
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.concurrent.TimeUnit

class AndroidBackgroundHandler(context: Context) : BackgroundHandler {

    private val workManager = WorkManager.getInstance(context)

    override fun scheduleTask(backgroundTask: BackgroundTaskType) {
        workManager.enqueueUniquePeriodicWork(
            backgroundTask.name,
            ExistingPeriodicWorkPolicy.UPDATE,
            getPeriodicWorkRequest(backgroundTask)
        )
    }

    override fun removeTask(backgroundTask: BackgroundTaskType) {
        workManager.cancelUniqueWork(backgroundTask.name)
    }

    private fun getPeriodicWorkRequest(backgroundTask: BackgroundTaskType) =
        PeriodicWorkRequestBuilder<DefaultWorker>(
            repeatInterval = backgroundTask.periodMinutes.toLong(),
            repeatIntervalTimeUnit = TimeUnit.MINUTES
        )
            .setConstraints(getConstraints())
            .setInputData(getInputData(backgroundTask))
            .build()

    private fun getInputData(backgroundTask: BackgroundTaskType) = Data.Builder()
        .putString(PARAM_NAME, backgroundTask.name)
        .build()

    private fun getConstraints() = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    companion object {
        private const val PARAM_NAME = "task_type"
    }

    class DefaultWorker(
        context: Context,
        workerParameters: WorkerParameters
    ) : CoroutineWorker(context, workerParameters), KoinComponent {
        private val taskType = BackgroundTaskType.valueOf(workerParameters.inputData.getString(PARAM_NAME)!!)

        override suspend fun doWork(): Result {
            val task: Lazy<BackgroundTask> = when (taskType) {
                BackgroundTaskType.NewsNotifications -> inject<NewsNotificationsBackgroundTask>()
                BackgroundTaskType.SubjectNotifications -> inject<SubjectNotificationsBackgroundTask>()
            }
            task.value.execute()
            return Result.success()
        }
    }
}

