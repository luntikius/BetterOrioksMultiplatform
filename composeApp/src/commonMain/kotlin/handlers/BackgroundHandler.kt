package handlers

import model.background.BackgroundTaskName

interface BackgroundHandler {

    fun scheduleTask(backgroundTask: BackgroundTaskName)

    fun removeTask(backgroundTask: BackgroundTaskName)

}

