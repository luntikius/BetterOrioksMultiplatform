package database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import model.ScheduleClass

@Dao
interface ScheduleDao {
    @Insert
    suspend fun insert(scheduleClass: ScheduleClass)

    @Delete
    suspend fun delete(scheduleClass: ScheduleClass)

    @Query("SELECT * FROM schedule")
    suspend fun getAll(): Flow<List<ScheduleClass>>
}
