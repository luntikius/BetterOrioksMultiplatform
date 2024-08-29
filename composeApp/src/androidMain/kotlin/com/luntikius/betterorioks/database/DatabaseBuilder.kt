package com.luntikius.betterorioks.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import database.ScheduleDatabase
import kotlinx.coroutines.Dispatchers

class DatabaseBuilder {

    private fun getDatabaseBuilder(ctx: Context): RoomDatabase.Builder<ScheduleDatabase> {
        val appContext = ctx.applicationContext
        val dbFile = appContext.getDatabasePath("schedule.db")
        return Room.databaseBuilder<ScheduleDatabase>(
            context = appContext,
            name = dbFile.absolutePath
        ).setDriver(BundledSQLiteDriver()).setQueryCoroutineContext(Dispatchers.IO)
    }

    fun getDatabase(ctx: Context): ScheduleDatabase {
        return getDatabaseBuilder(ctx).build()
    }

}