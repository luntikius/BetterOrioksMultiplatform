package model.database

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "weeks", indices = [Index(value = ["number"], unique = true)])
data class ScheduleWeekEntity(
    @PrimaryKey
    val number: Int,
    val type: String
)