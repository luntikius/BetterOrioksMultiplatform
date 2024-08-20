package model

data class ScheduleWeek(
    val number: Int,
    val type: WeekType,
    val days: List<ScheduleDay>
)
