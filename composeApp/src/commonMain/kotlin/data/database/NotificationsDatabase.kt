package data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import model.database.notifications.NotificationsSubjectEntity

@Database(
    entities = [
        NotificationsSubjectEntity::class
    ],
    version = 1,
    exportSchema = true
)
@ConstructedBy(ScheduleDatabaseConstructor::class)
abstract class NotificationsDatabase : RoomDatabase() {

    abstract fun getDao(): ScheduleDao

    companion object {

        private var Instance: NotificationsDatabase? = null
        fun getRoomDatabase(
            builder: Builder<NotificationsDatabase>
        ): NotificationsDatabase {
            return Instance ?: builder
                .setDriver(BundledSQLiteDriver())
                .setQueryCoroutineContext(Dispatchers.IO)
                .fallbackToDestructiveMigrationOnDowngrade(true)
                .build()
                .also { Instance = it }
        }
    }
}

@Suppress("NO_ACTUAL_FOR_EXPECT", "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect object NotificationsDatabaseConstructor : RoomDatabaseConstructor<ScheduleDatabase>
