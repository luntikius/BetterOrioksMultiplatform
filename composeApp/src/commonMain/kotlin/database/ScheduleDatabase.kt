package database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import model.database.FirstOfTheMonthEntity
import model.database.ScheduleDayEntity
import model.database.ScheduleElementEntity
import model.database.ScheduleWeekEntity

@Database(
    entities = [
        ScheduleElementEntity::class,
        ScheduleDayEntity::class,
        ScheduleWeekEntity::class,
        FirstOfTheMonthEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class ScheduleDatabase : RoomDatabase() {

    abstract fun getDao(): ScheduleDao

    companion object {

        private var Instance: ScheduleDatabase? = null
        fun getRoomDatabase(
            builder: Builder<ScheduleDatabase>
        ): ScheduleDatabase {
            return Instance ?: builder
                .setDriver(BundledSQLiteDriver())
                .setQueryCoroutineContext(Dispatchers.IO)
                .fallbackToDestructiveMigrationOnDowngrade(true)
                .build()
                .also { Instance = it }
        }
    }
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<ScheduleDatabase>
