package model.database

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import model.ScheduleDay
import model.ScheduleWeek
import model.WeekType

@Entity(tableName = "weeks", indices = [Index(value = ["id"], unique = true)])
data class ScheduleWeekEntity(
    @PrimaryKey
    val id: Int,
    val type: String
) {
    fun toScheduleWeek(days: List<ScheduleDay>): ScheduleWeek {
        return ScheduleWeek(
            number = id,
            type = WeekType.valueOf(type),
            days = days
        )
    }
}
