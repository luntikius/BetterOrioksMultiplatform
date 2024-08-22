package model

import kotlinx.serialization.SerialName
@kotlinx.serialization.Serializable
data class FullSchedule(
    @SerialName("Times")
    val timeTable: List<ScheduleTimeCodes> = listOf(),
    @SerialName("Data")
    val schedule: List<FullScheduleElement> = listOf(),
    @SerialName("Semester")
    val semester: String = ""
)