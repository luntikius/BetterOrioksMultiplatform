package model

import kotlinx.datetime.LocalDate

data class ScheduleDay(
    val date: LocalDate,
    val scheduleList: List<ScheduleElement>
)
