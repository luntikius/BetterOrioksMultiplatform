package handlers

import android.content.Context
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import model.background.BackgroundTaskType
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
}

