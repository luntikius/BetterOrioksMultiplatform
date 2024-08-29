package model.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
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
