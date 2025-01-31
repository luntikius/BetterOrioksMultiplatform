package model.schedule.scheduleJson

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
class ScheduleFromSiteSubject(
    @SerialName("Code")
    val code: Int,
    @SerialName("Name")
    val name: String,
    @SerialName("TeacherFull")
    val teacherFull: String,
    @SerialName("Teacher")
    val teacher: String,
    @SerialName("Form")
    val form: String
) {
    val formFromString =
        if (name.contains("[")) {
            name.slice(name.indexOf("[") + 1 until name.indexOf("]"))
        } else {
            "Н/С"
        }

    val nameFromString =
        if (name.contains("[")) {
            name.slice(0 until name.indexOf("["))
        } else {
            name
        }
}
