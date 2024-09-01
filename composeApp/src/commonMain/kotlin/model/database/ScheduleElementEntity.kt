package model.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalTime
import model.ScheduleClass

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
    val isSwitchable: Boolean,
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
            isSwitchable = isSwitchable
        )
    }
}
