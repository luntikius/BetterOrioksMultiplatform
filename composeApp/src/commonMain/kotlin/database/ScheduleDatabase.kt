package database

import androidx.room.Database
import androidx.room.RoomDatabase
import model.database.ScheduleElementEntity

@Database(entities = [ScheduleElementEntity::class], version = 1)
abstract class ScheduleDatabase : RoomDatabase() {

    abstract fun getDao(): ScheduleDao

}
