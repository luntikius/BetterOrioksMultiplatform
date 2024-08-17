package model

import kotlinx.datetime.LocalDate

data class Schedule(
    val days: List<ScheduleDay>,
    val firstOfTheMonths: List<LocalDate>
)
