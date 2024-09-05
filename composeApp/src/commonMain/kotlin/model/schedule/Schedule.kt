package model.schedule

import kotlinx.datetime.LocalDate

data class Schedule(
    val weeks: List<ScheduleWeek>,
    val firstOfTheMonths: List<LocalDate>
)
