package model.background

interface BackgroundTask {

    suspend fun execute(silently: Boolean)
}
