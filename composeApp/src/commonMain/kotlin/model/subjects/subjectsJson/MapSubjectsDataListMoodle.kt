package model.subjects.subjectsJson

import kotlinx.serialization.SerialName
import model.schedule.scheduleJson.Semester
import model.subjects.subjectsJson.jsonElements.SubjectFromWeb

@kotlinx.serialization.Serializable
data class MapSubjectsDataListMoodle(
    @SerialName("dises")
    private val mapSubjects: Map<String, SubjectFromWeb> = mapOf(),
    @SerialName("offset_dises")
    override val offsetSubjects: List<SubjectFromWeb> = listOf(),
    @SerialName("dolg_dises")
    override val debts: List<SubjectFromWeb> = listOf(),
    @SerialName("sems")
    override val semesters: List<Semester> = listOf(),
    @SerialName("dises_moodle")
    override val subjectsWithMoodleIds: List<String> = listOf()
) : SubjectsData {
    override val subjects: List<SubjectFromWeb>
        get() = mapSubjects.values.toList()
}
