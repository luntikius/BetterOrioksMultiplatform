package model

import kotlinx.serialization.SerialName
@kotlinx.serialization.Serializable
data class FullScheduleElement(
    @SerialName("Day")
    val day: Int,
    @SerialName("DayNumber")
    val dayNumber:Int,
    @SerialName("Time")
    val time: ScheduleTimeCodes,
    @SerialName("Class")
    val subject: ScheduleSubject,
    @SerialName("Group")
    val group: Group,
    @SerialName("Classroom")
    val room: Classroom
)