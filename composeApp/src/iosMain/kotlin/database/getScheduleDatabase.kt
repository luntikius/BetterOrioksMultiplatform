package database

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver

fun getScheduleDatabase(): ScheduleDatabase{
    val dbFile = NSHomeDirectory() + "/schedule.db"
    return Room.databaseBuilder<ScheduleDatabase>(
        name = dbFile,
        factory = { ScheduleDatabase::class.instantiateImpl() }
    )
        .setDriver(BundledSQLiteDriver())
        .build()
}
