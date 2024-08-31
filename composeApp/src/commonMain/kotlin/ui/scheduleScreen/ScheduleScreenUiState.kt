package ui.scheduleScreen

import model.Schedule
import model.ScheduleDay
import model.ScheduleWeek

data class ScheduleScreenUiState(
    val schedule: Schedule,
    val weeks: List<ScheduleWeek> = schedule.weeks,
    val days: List<ScheduleDay> = schedule.weeks.flatMap { it.days },
    val selectedDayIndex: Int = 0,
    val selectedWeekIndex: Int = 0,
    val isDayAutoScrollInProgress: Boolean = false,
    val isWeekAutoScrollInProgress: Boolean = false
)
