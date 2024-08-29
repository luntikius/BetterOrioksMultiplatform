package model.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

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
    val date: String,
    val weekType: String
)