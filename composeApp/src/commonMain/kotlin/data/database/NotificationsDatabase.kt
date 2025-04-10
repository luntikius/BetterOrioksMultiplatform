package data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import model.database.notifications.NotificationEntity
import model.database.notifications.NotificationsNewsEntity
import model.database.notifications.NotificationsSubjectEntity

@Database(
    entities = [
        NotificationsSubjectEntity::class,
        NotificationsNewsEntity::class,
        NotificationEntity::class,
    ],
    version = 4,
    exportSchema = true,
)
@TypeConverters(OrioksConverter::class)
@ConstructedBy(NotificationsDatabaseConstructor::class)
abstract class NotificationsDatabase : RoomDatabase() {

    abstract fun getDao(): NotificationsDao

    companion object {

        private var Instance: NotificationsDatabase? = null
        fun getRoomDatabase(
            builder: Builder<NotificationsDatabase>
        ): NotificationsDatabase {
            return Instance ?: builder
                .setDriver(BundledSQLiteDriver())
                .setQueryCoroutineContext(Dispatchers.IO)
                .fallbackToDestructiveMigrationOnDowngrade(true)
                .fallbackToDestructiveMigrationFrom(true, 1, 2, 3)
                .build()
                .also { Instance = it }
        }
    }
}

@Suppress("NO_ACTUAL_FOR_EXPECT", "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect object NotificationsDatabaseConstructor : RoomDatabaseConstructor<NotificationsDatabase>
