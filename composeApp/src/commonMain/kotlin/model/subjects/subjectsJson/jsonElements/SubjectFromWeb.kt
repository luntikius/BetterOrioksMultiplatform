package model.subjects.subjectsJson.jsonElements

import kotlinx.serialization.SerialName
import model.subjects.SubjectListItem

@kotlinx.serialization.Serializable
class SubjectFromWeb(
    @SerialName("id")
    val id: Int = 0,
    @SerialName("name")
    val name: String = "",
    @SerialName("id_science")
    val scienceId: Int = 0,
//    @SerialName("id_form_control")
//    val formOfControlId: Int = 0,
    @SerialName("id_dis")
    val disciplineId: Int = 0,
    @SerialName("segments")
    private val segments: List<Segment> = listOf(),
    @SerialName("formControl")
    val formOfControl: FormOfControl = FormOfControl(),
    @SerialName("preps")
    val teachers: List<Teacher> = listOf(),
    @SerialName("grade")
    private val grade: Grade = Grade(),
    @SerialName("date_exam")
    val examDate: String = "",
    @SerialName("time_exam")
    val examTime: String = "",
    @SerialName("room_exam")
    val examRoom: String = "",
    @SerialName("debt")
    val isDebt: Boolean = false,
//    @SerialName("mvb")
//    val maxAvailableScore: Double = 0.0,
    @SerialName("is_exam_time")
    val isExamTime: Boolean = false,
    @SerialName("debtKms")
    val debtControlEvents: List<DebtControlEvent> = listOf()
) {
    val controlEvents: List<ControlEvent> = segments.map { it.allControlEvents }.flatten()

    val currentPoints: String = grade.fullPoints

    val maxPoints: Double
        get() {
            val controlEvents = controlEvents
            var maxScore = 0.0
            controlEvents.forEach {
                if (it.grade.currentPoints != "-") {
                    val isBonus = it.bonus != 0
                    if (isBonus) {
                        maxScore += it.grade.currentPoints.toIntOrNull() ?: 0
                    } else {
                        maxScore += it.maxScore
                    }
                }
            }
            return maxScore
        }

    fun toSubjectListItem(): SubjectListItem =
        SubjectListItem(
            id = id.toString(),
            name = name,
            currentPoints = currentPoints,
            maxPoints = maxPoints.toString(),
            formOfControl = formOfControl.id
        )
}
