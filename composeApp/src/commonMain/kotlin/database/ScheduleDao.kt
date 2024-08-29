package database

import androidx.room.Dao
import androidx.room.Insert

import model.database.ScheduleElementEntity

@Dao
interface ScheduleDao {

    @Insert
    suspend fun insert(item: ScheduleElementEntity)

}