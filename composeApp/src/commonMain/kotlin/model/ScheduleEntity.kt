package model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "schedule")
data class ScheduleEntity(
    @PrimaryKey val date: String,
    @ColumnInfo(name = "number") val number: Int = 0,
    @ColumnInfo(name = "from") val fromTime: String = "11:11",
    @ColumnInfo(name = "to") val toTime: String = "12:46",
    @ColumnInfo(name = "type") val type: String = "Пара",
    @ColumnInfo(name = "subject") val subject: String = "Название предмета",
    @ColumnInfo(name = "teacher") val teacher: String = "Учитель",
    @ColumnInfo(name = "room") val room: String = "",
)
