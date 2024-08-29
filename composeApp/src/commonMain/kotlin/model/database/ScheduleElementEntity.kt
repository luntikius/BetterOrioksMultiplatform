package model.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

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
)
