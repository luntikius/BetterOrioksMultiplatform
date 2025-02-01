package database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import data.database.NotificationsDatabase
import data.database.ScheduleDatabase

fun getScheduleDatabaseBuilder(ctx: Context): RoomDatabase.Builder<ScheduleDatabase> {
    val appContext = ctx.applicationContext
    val dbFile = appContext.getDatabasePath("schedule.db")
    return Room.databaseBuilder<ScheduleDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}

fun getNotificationsDatabaseBuilder(ctx: Context): RoomDatabase.Builder<NotificationsDatabase> {
    val appContext = ctx.applicationContext
    val dbFile = appContext.getDatabasePath("notifications.db")
    return Room.databaseBuilder<NotificationsDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}
