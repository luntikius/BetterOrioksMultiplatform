package database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.MapColumn
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import model.database.FirstOfTheMonthEntity
import model.database.ScheduleDayEntity
import model.database.ScheduleElementEntity
import model.database.ScheduleWeekEntity

@Dao
interface ScheduleDao {

    @Insert
    suspend fun insert(item: ScheduleElementEntity)

    @Insert
    suspend fun insert(item: ScheduleDayEntity)

    @Insert
    suspend fun insert(item: ScheduleWeekEntity)

    @Insert
    suspend fun insert(item: FirstOfTheMonthEntity)

    @Query("SELECT * FROM elements")
    fun getScheduleElementsByDaysFlow(): Flow<Map<@MapColumn("dayId")Int, @MapColumn("*")List<ScheduleElementEntity>>>

    @Query("SELECT * FROM days")
    fun getScheduleDaysByWeeksFlow(): Flow<List<ScheduleDayEntity>>

    @Query("SELECT * FROM weeks")
    fun getWeeksFlow(): Flow<List<ScheduleWeekEntity>>

    @Query("SELECT * FROM firstOfTheMonths")
    fun getFirstOfTheMonthsFlow(): Flow<List<FirstOfTheMonthEntity>>
}
