package database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import model.database.ScheduleElementEntity

@Database(entities = [ScheduleElementEntity::class], version = 1)
abstract class ScheduleDatabase : RoomDatabase() {

    abstract fun getDao(): ScheduleDao
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<ScheduleDatabase>

fun getRoomDatabase(
    builder: RoomDatabase.Builder<ScheduleDatabase>
): ScheduleDatabase {
    return builder
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}
