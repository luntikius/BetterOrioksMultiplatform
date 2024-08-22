package model

data class ScheduleWeek(
    val number: Int,
    val type: String,
    val days: List<ScheduleDay>
)