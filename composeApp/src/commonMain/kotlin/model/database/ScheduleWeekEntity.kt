package model.database

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import model.ScheduleDay
import model.ScheduleWeek
import model.WeekType

@Entity(tableName = "weeks", indices = [Index(value = ["number"], unique = true)])
data class ScheduleWeekEntity(
    @PrimaryKey
    val number: Int,
    val type: String
) {
    fun ScheduleWeekEntity.toScheduleWeek(days: List<ScheduleDay>): ScheduleWeek {
        return ScheduleWeek(
            number = number,
            type = WeekType.valueOf(type),
            days = days
        )
    }
}