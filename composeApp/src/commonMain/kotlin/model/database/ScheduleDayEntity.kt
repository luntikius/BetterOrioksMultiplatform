package model.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate
import model.schedule.ScheduleDay
import model.schedule.ScheduleElement

@Entity(
    tableName = "days",
    indices = [Index(value = ["weekId"], unique = false), Index(value = ["id"], unique = true)],
    foreignKeys = [
        ForeignKey(
            entity = ScheduleWeekEntity::class,
            parentColumns = ["id"],
            childColumns = ["weekId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ScheduleDayEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val weekId: Int,
    val date: String
) {
    fun toScheduleDay(scheduleList: List<ScheduleElement>): ScheduleDay {
        return ScheduleDay(
            date = LocalDate.parse(date),
            weekNumber = weekId,
            scheduleList = scheduleList
        )
    }
}
