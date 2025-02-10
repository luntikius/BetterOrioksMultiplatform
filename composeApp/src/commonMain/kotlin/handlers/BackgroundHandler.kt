package handlers

import model.background.BackgroundTaskType

interface BackgroundHandler {

    fun scheduleTask(backgroundTask: BackgroundTaskType)

    fun removeTask(backgroundTask: BackgroundTaskType)
}

