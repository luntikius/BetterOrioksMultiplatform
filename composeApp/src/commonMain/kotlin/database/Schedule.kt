package database

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

@Entity
data class Schedule(
    @PrimaryKey val date: LocalDate,
    val number: Int,
    val fromTime: LocalTime,
    val toTime: LocalTime,
    val type: String,
    val subject: String,
    val teacher: String,
    val room: String,
    val isSwitchable: Boolean
)
