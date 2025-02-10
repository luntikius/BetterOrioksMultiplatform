package model.background

interface BackgroundTask {
    val name: BackgroundTaskName

    val intervalMinutes: Int

    suspend fun execute()
}