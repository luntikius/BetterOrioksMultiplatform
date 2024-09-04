package database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import data.database.ScheduleDatabase

fun getDatabaseBuilder(ctx: Context): RoomDatabase.Builder<ScheduleDatabase> {
    val appContext = ctx.applicationContext
    val dbFile = appContext.getDatabasePath("schedule.db")
    return Room.databaseBuilder<ScheduleDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}
