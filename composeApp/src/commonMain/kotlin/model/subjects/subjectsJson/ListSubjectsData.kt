package model.subjects.subjectsJson

import kotlinx.serialization.SerialName
import model.schedule.scheduleJson.Semester
import model.subjects.subjectsJson.jsonElements.SubjectFromWeb

@kotlinx.serialization.Serializable
class ListSubjectsData(
    @SerialName("dises")
    override val subjects: List<SubjectFromWeb> = listOf(),
    @SerialName("offset_dises")
    override val offsetSubjects: List<SubjectFromWeb> = listOf(),
    @SerialName("dolg_dises")
    override val debts: List<SubjectFromWeb> = listOf(),
    @SerialName("sems")
    override val semesters: List<Semester> = listOf(),
    @SerialName("dises_moodle")
    private val subjectsWithMoodleIdsMap: Map<String, String> = mapOf()
) : SubjectsData {
    override val subjectsWithMoodleIds: List<String>
        get() = subjectsWithMoodleIdsMap.keys.toList()
}

