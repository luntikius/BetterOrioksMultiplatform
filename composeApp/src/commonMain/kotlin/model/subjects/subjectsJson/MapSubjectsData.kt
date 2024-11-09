package model.subjects.subjectsJson

import kotlinx.serialization.SerialName
import model.scheduleJson.Semester
import model.subjects.subjectsJson.jsonElements.SubjectFromWeb

@kotlinx.serialization.Serializable
data class MapSubjectsData(
    @SerialName("dises")
    private val mapSubjects: Map<String, SubjectFromWeb> = mapOf(),
    @SerialName("dolg_dises")
    override val debts: List<SubjectFromWeb> = listOf(),
    @SerialName("sems")
    override val semesters: List<Semester> = listOf()
) : SubjectsData {
    override val subjects: List<SubjectFromWeb>
        get() = mapSubjects.values.toList()
}