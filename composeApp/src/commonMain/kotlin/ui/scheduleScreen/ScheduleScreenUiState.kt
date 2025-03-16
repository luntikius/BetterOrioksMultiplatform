package ui.scheduleScreen

import model.schedule.Schedule
import model.schedule.ScheduleClass
import model.schedule.ScheduleDay
import model.schedule.ScheduleWeek

data class ScheduleScreenUiState(
    val schedule: Schedule,
    val weeks: List<ScheduleWeek> = schedule.weeks,
    val days: List<ScheduleDay> = schedule.weeks.flatMap { it.days },
    val selectedDayIndex: Int = 0,
    val selectedWeekIndex: Int = 0,
    val isDayAutoScrollInProgress: Boolean = false,
    val isWeekAutoScrollInProgress: Boolean = false,
    val switchElement: ScheduleClass = ScheduleClass(),
    val isRefreshAlertVisible: Boolean = false
)
