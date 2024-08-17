package ui.scheduleScreen

import model.Schedule
import model.ScheduleDay

data class ScheduleScreenUiState(
    val schedule: Schedule,
    val isRefreshing: Boolean = false,
    val days: List<ScheduleDay> = schedule.days,
    val selectedDay: ScheduleDay = days.first()
) {
    val selectedDayIndex = days.indexOf(selectedDay)
}
