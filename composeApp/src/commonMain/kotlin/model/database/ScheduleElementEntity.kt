package model.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalTime
import model.schedule.ScheduleClass

@Entity(
    tableName = "elements",
    indices = [Index(value = ["dayId"], unique = false)],
    foreignKeys = [
        ForeignKey(
            entity = ScheduleDayEntity::class,
            parentColumns = ["id"],
            childColumns = ["dayId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ScheduleElementEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val dayId: Int = 0,
    val number: Int,
    val fromTime: String,
    val toTime: String,
    val type: String,
    val subject: String,
    val teacher: String,
    val room: String,
) {
    fun toScheduleClass(): ScheduleClass {
        return ScheduleClass(
            day = dayId,
            number = number,
            fromTime = LocalTime.parse(fromTime),
            toTime = LocalTime.parse(toTime),
            type = type,
            subject = subject,
            teacher = teacher,
            room = room,
            isSwitchable = number in SWITCHABLE_NUMBERS
        )
    }

    companion object {
        private val SWITCHABLE_NUMBERS = listOf(3)
    }
}
