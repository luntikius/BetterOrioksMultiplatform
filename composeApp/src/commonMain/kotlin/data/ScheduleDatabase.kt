package data

import androidx.room.Database
import androidx.room.RoomDatabase
import model.ScheduleEntity

@Database(entities = [ScheduleEntity::class], version = 1)
abstract class ScheduleDatabase: RoomDatabase() {
    abstract fun scheduleDao(): ScheduleDao
}
