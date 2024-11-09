package model.schedule.scheduleJson

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import model.schedule.SemesterDates

@Serializable
data class Semester(
    @SerialName("id")
    val id: String,
    @SerialName("year")
    val year: String,
    @SerialName("date_start")
    val startDate: String?,
    @SerialName("session_start")
    val sessionStart: String?,
    @SerialName("session_end")
    val sessionEnd: String?,
    @SerialName("name")
    val name: String
) {
    fun toSemesterDates(): SemesterDates {

        return SemesterDates(
            startDate = startDate!!,
            sessionStartDate = sessionStart,
            sessionEndDate = sessionEnd
        )
    }
}
