package model

interface ScheduleElement {
    val day: Int
    val number: Int
}

data class ScheduleClass (
    override val day: Int = 0,
    override val number: Int = 0,
    var from: String = "",
    var to: String = "",
    val type: String = "Пара",
    val name: String = "",
    val teacher: String = "",
    val room: String = "",
): ScheduleElement

data class ScheduleGap (
    override val day: Int = 0,
    override val number: Int = 0,
    val windowDuration: Int = 0
): ScheduleElement