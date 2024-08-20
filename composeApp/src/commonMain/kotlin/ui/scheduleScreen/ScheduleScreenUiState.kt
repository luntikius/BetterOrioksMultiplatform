package ui.scheduleScreen

import model.Schedule
import model.ScheduleDay
import model.ScheduleWeek

data class ScheduleScreenUiState(
    val schedule: Schedule,
    val weeks: List<ScheduleWeek> = schedule.weeks,
    val days: List<ScheduleDay> = schedule.weeks.flatMap { it.days },
    val isRefreshing: Boolean = false,
    val selectedDay: ScheduleDay = days.first()
) {
    val selectedDayIndex = days.indexOf(selectedDay)
}
