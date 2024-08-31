package model.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalTime
import model.ScheduleClass
import model.ScheduleElement
import model.ScheduleGap

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
    val id: Int,
    val dayId: Int,
    val isGap: Boolean,

    // ScheduleClass
    val number: Int,
    val fromTime: String,
    val toTime: String,
    val type: String,
    val subject: String,
    val teacher: String,
    val room: String,
    val isSwitchable: Boolean,

    // ScheduleGap
    val gapDuration: Int
) {
    fun toScheduleElement(): ScheduleElement {
        return if (isGap) {
            ScheduleGap(
                day = dayId,
                number = number,
                gapDuration = gapDuration
            )
        } else {
            ScheduleClass(
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
}
