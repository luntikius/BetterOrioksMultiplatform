package data.database

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
    suspend fun insertElement(item: ScheduleElementEntity)

    @Insert
    suspend fun insertDay(item: ScheduleDayEntity)

    @Insert
    suspend fun insertWeek(item: ScheduleWeekEntity)

    @Insert
    suspend fun insertFirstOfTheMonth(item: FirstOfTheMonthEntity)

    @Insert
    suspend fun insertAllElements(item: List<ScheduleElementEntity>)

    @Insert
    suspend fun insertAllDays(item: List<ScheduleDayEntity>)

    @Insert
    suspend fun insertAllWeeks(item: List<ScheduleWeekEntity>)

    @Insert
    suspend fun insertAllFirstOfTheMonths(item: List<FirstOfTheMonthEntity>)

    @Query("SELECT * FROM elements")
    fun getScheduleElementsByDaysFlow(): Flow<
        Map<
            @MapColumn("dayId")
            Int,
            @MapColumn("*")
            List<ScheduleElementEntity>
            >
        >

    @Query("SELECT * FROM days")
    fun getScheduleDaysByWeeksFlow(): Flow<List<ScheduleDayEntity>>

    @Query("SELECT * FROM weeks")
    fun getWeeksFlow(): Flow<List<ScheduleWeekEntity>>

    @Query("SELECT * FROM firstOfTheMonths")
    fun getFirstOfTheMonthsFlow(): Flow<List<FirstOfTheMonthEntity>>

    @Query("DELETE FROM weeks")
    suspend fun dumpSchedule()

    @Query("DELETE FROM firstOfTheMonths")
    suspend fun dumpFirstOfTheMonths()

    @Query("SELECT COUNT(*) FROM elements")
    suspend fun countEntities(): Int

    @Query(
        "UPDATE elements " +
            "SET fromTime = :fromTime, toTime = :toTime " +
            "WHERE number = :number AND subject = :subject AND (dayId % :dayCount) = (:day % :dayCount)"
    )
    suspend fun updateWindows(day: Int, number: Int, subject: String, fromTime: String, toTime: String, dayCount: Int)
}
