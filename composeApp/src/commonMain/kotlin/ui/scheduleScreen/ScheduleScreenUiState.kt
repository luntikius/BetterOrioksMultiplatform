package ui.scheduleScreen

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus
import model.ScheduleClass
import model.ScheduleDay
import model.ScheduleGap

data class ScheduleScreenUiState(
    val isRefreshing: Boolean = false,
    val schedule: List<ScheduleDay> = buildList {
        repeat(100) {
            add(
                ScheduleDay(
                    date = LocalDate(2024, 8, 9).plus(it, DateTimeUnit.DAY),
                    weekNumber = it / 7,
                    scheduleList = listOf(
                        ScheduleClass(number = 1),
                        ScheduleClass(number = 2),
                        ScheduleGap(gapDuration = 1),
                        ScheduleClass(number = 3),
                        ScheduleGap(gapDuration = 3),
                        ScheduleClass(number = 4),
                        ScheduleGap(gapDuration = 5),
                        ScheduleClass(number = 5),
                        ScheduleGap(gapDuration = 11),
                        ScheduleClass(number = 6),
                        ScheduleGap(gapDuration = 31)
                    )
                )
            )
        }
    },
    val selectedDay: ScheduleDay = schedule.first(),
) {
    val selectedDayIndex = schedule.indexOf(selectedDay)
}
