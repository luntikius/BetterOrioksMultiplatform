package ui.scheduleScreen

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import model.ScheduleClass
import model.ScheduleDay
import model.ScheduleGap

data class ScheduleScreenUiState(
    val selectedDate: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
    val isRefreshing: Boolean = false,
    val schedule: List<ScheduleDay> = buildList {
        repeat(25) {
            add(
                ScheduleDay(
                    date = LocalDate(2024, 8, 9).plus(it, DateTimeUnit.DAY),
                    scheduleList = listOf(
                        ScheduleClass(number = 1),
                        ScheduleGap(gapDuration = 1),
                        ScheduleClass(number = 2),
                        ScheduleGap(gapDuration = 3),
                        ScheduleClass(number = 3),
                        ScheduleGap(gapDuration = 5),
                        ScheduleClass(number = 4),
                        ScheduleGap(gapDuration = 11),
                        ScheduleClass(number = 5),
                        ScheduleGap(gapDuration = 31)
                    )
                )
            )
        }
    }
) {
    val selectedDayIndex = schedule.indexOfFirst { it.date == selectedDate }
}
