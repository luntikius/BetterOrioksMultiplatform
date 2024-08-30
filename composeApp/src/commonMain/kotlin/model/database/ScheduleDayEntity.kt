package model.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate
import model.ScheduleDay
import model.ScheduleElement

@Entity(
    tableName = "days",
    indices = [Index(value = ["weekNumber"], unique = false), Index(value = ["id"], unique = true)],
    foreignKeys = [
        ForeignKey(
            entity = ScheduleWeekEntity::class,
            parentColumns = ["number"],
            childColumns = ["weekNumber"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ScheduleDayEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val weekNumber: Int,
    val date: String
) {
    fun ScheduleDayEntity.toScheduleDay(scheduleList: List<ScheduleElement>): ScheduleDay {
        return ScheduleDay(
            date = LocalDate.parse(date),
            weekNumber = weekNumber,
            scheduleList = scheduleList
        )
    }
}
