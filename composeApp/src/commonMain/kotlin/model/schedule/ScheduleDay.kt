package model.schedule

import kotlinx.datetime.LocalDate

data class ScheduleDay(
    val date: LocalDate,
    val weekNumber: Int,
    val scheduleList: List<ScheduleElement>,
)
