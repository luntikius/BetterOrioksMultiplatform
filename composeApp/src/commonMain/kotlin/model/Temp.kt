package model

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus

object Temp {
    val schedule = Schedule(
        days = buildList {
            repeat(100) {
                add(
                    ScheduleDay(
                        date = LocalDate(2024, 8, 12).plus(it, DateTimeUnit.DAY),
                        weekNumber = it / 7 + 1,
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
        firstOfTheMonths = listOf(
            LocalDate(2024, 8, 12),
            LocalDate(2024, 9, 1),
            LocalDate(2024, 10, 1),
            LocalDate(2024, 11, 1)
        )
    )
}