package ui.scheduleScreen

import model.ScheduleClass
import model.ScheduleElement
import model.ScheduleGap

data class ScheduleScreenUiState (
    val isRefreshing: Boolean = false,
    val schedule: List<List<ScheduleElement>> = listOf(
        listOf(
            ScheduleGap(gapDuration = 1),
            ScheduleClass(number = 1),
            ScheduleGap(gapDuration = 2),
            ScheduleClass(number = 2),
            ScheduleGap(gapDuration = 3),
            ScheduleClass(number = 3),
            ScheduleGap(gapDuration = 5),
            ScheduleClass(number = 4),
            ScheduleGap(gapDuration = 11),
            ScheduleClass(number = 5),
            ScheduleGap(gapDuration = 31)
        ),
        listOf(
            ScheduleGap(gapDuration = 1),
            ScheduleClass(number = 1),
            ScheduleGap(gapDuration = 2),
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