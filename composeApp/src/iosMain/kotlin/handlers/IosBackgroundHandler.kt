package handlers

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.datetime.toNSDateComponents
import model.background.BackgroundTaskType
import platform.BackgroundTasks.BGAppRefreshTaskRequest
import platform.BackgroundTasks.BGProcessingTaskRequest
import platform.BackgroundTasks.BGTaskScheduler
import utils.now

@OptIn(ExperimentalForeignApi::class)
class IosBackgroundHandler : BackgroundHandler {

    override fun scheduleTask(backgroundTask: BackgroundTaskType) {
        removeTask(backgroundTask)
        val request = when (backgroundTask) {
            BackgroundTaskType.SubjectNotifications ->
                BGAppRefreshTaskRequest(identifier = backgroundTask.getTaskIdentifier())
            else ->
                BGProcessingTaskRequest(identifier = backgroundTask.getTaskIdentifier()).apply {
                    requiresNetworkConnectivity = true
                }
        }
        request.earliestBeginDate = now(offsetSeconds = backgroundTask.periodMinutes * 60).toNSDateComponents().date

        try {
            check(
                BGTaskScheduler.sharedScheduler.submitTaskRequest(request, null)
            )
            println("$backgroundTask task submitted successfully")
        } catch (e: Exception) {
            println("Failed to schedule background task $backgroundTask: ${e.message}")
        }
    }

    override fun removeTask(backgroundTask: BackgroundTaskType) {
        BGTaskScheduler.sharedScheduler.cancelTaskRequestWithIdentifier(backgroundTask.getTaskIdentifier())
        println("$backgroundTask task removed successfully")
    }

    private fun BackgroundTaskType.getTaskIdentifier(): String = "com.luntikius.betterorioks.$name"
}
