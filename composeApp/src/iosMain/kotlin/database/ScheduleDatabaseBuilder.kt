package database

import androidx.room.Room
import androidx.room.RoomDatabase
import data.database.NotificationsDatabase
import data.database.ScheduleDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

fun getScheduleDatabaseBuilder(): RoomDatabase.Builder<ScheduleDatabase> {
    val dbFilePath = "${documentDirectory()}/schedule.db"
    return Room.databaseBuilder<ScheduleDatabase>(
        name = dbFilePath
    )
}

fun getNotificationsDatabaseBuilder(): RoomDatabase.Builder<NotificationsDatabase> {
    val dbFilePath = "${documentDirectory()}/notifications.db"
    return Room.databaseBuilder<NotificationsDatabase>(
        name = dbFilePath
    )
}

@OptIn(ExperimentalForeignApi::class)
private fun documentDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory?.path)
}
