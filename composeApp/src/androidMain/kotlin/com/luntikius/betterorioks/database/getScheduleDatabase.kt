package com.luntikius.betterorioks.database

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import database.ScheduleDatabase

fun getScheduleDatabase(context: Context): ScheduleDatabase{
    val dbFile = context.getDatabasePath("schedule.db")
    return Room.databaseBuilder<ScheduleDatabase>(
        context = context.applicationContext,
        name = dbFile.absolutePath
    )
        .setDriver(BundledSQLiteDriver())
        .build()
}
