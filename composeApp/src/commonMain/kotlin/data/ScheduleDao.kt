package data

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import model.ScheduleEntity

interface ScheduleDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(schedule: ScheduleEntity)

    @Query("SELECT * from schedule WHERE date = :date")
    fun getSchedule(date: String): Flow<List<ScheduleEntity>>

    @Query("DELETE FROM schedule")
    suspend fun dump()

    @Query("SELECT COUNT(*) FROM schedule")
    suspend fun count():Int
}
