package model

import kotlinx.datetime.LocalDate

data class ScheduleDay(
    val date: LocalDate,
    val weekNumber: Int,
    val scheduleList: List<ScheduleElement>,
    val weekType: WeekType = WeekType.entries[weekNumber % 4]
)
