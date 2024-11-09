package model.schedule.scheduleJson

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import model.schedule.SemesterDates

@Serializable
data class SubjectsSemesters(
    @SerialName("sems")
    val semesters: List<Semester>
) {
    fun getLastSemesterDates(): SemesterDates {
        return semesters.last { it.startDate != null }.toSemesterDates()
    }
}
