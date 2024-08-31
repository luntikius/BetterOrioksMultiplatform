package model

import kotlinx.datetime.LocalTime

interface ScheduleElement {
    val day: Int
    val number: Int
}

data class ScheduleClass(
    override val day: Int = 0,
    override val number: Int = 0,
    val fromTime: LocalTime = LocalTime(11, 11),
    val toTime: LocalTime = LocalTime(12, 46),
    val type: String = "Пара",
    val subject: String = "Название предмета",
    val teacher: String = "Учитель",
    val room: String = ""
) : ScheduleElement

data class ScheduleGap(
    override val day: Int = 0,
    override val number: Int = 0,
    val gapDuration: Int = 0
) : ScheduleElement
