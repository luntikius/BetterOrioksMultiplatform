package model.schedule.scheduleJson

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class ScheduleFromSiteSubject(
    @SerialName("Name")
    val name: String,
    @SerialName("TeacherFull")
    val teacherFull: String,
    @SerialName("Teacher")
    val teacher: String,
) {
    val formFromString =
        if (name.contains("[")) {
            name.slice(name.indexOfLast { it == '[' } + 1 until name.indexOfLast { it == ']' })
        } else {
            "Н/С"
        }

    val nameFromString =
        if (name.contains("[")) {
            name.slice(0 until name.indexOfLast { it == '[' })
        } else {
            name
        }
}
