package model.subjects.subjectsJson.jsonElements

import kotlinx.datetime.LocalTime
import kotlinx.datetime.atTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import model.subjects.ExamInfo
import model.subjects.SubjectInfo
import model.subjects.SubjectListItem
import model.subjects.subjectsJson.SubjectsData.Companion.DEBT_POSTFIX
import model.subjects.subjectsJson.SubjectsData.Companion.NO_DEBT_POSTFIX
import utils.BetterOrioksFormats

@Serializable
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
    private val examDate: String = "",
    @SerialName("time_exam")
    private val examTime: String = "",
    @SerialName("room_exam")
    private val examRoom: String = "",
    @SerialName("date_cons")
    private val consultationDate: String = "",
    @SerialName("time_cons")
    private val consultationTime: String = "",
    @SerialName("room_cons")
    private val consultationRoom: String = "",
    @SerialName("debt")
    val isDebt: Boolean = false,
    @SerialName("debtKms")
    val debtControlEvents: List<DebtControlEvent> = listOf()
) {
    val controlEvents: List<ControlEvent> = segments.map { it.allControlEvents }.flatten()

    val currentPoints: String =
        controlEvents.sumOf { it.grade.currentPoints.toDoubleOrNull() ?: 0.0 }
            .toString().takeIf { it != "0" } ?: grade.fullPoints

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

    private fun getExamInfo(): ExamInfo? {
        return try {
            val date = BetterOrioksFormats.EXAM_DATE_FORMAT.parse(examDate)
            val time = LocalTime.parse(examTime)
            ExamInfo(
                date = date.atTime(time),
                room = examRoom,
            )
        } catch (e: IllegalArgumentException) {
            println(e.message)
            null
        }
    }

    private fun getConsultationInfo(): ExamInfo? {
        return try {
            val consultationDate = BetterOrioksFormats.EXAM_DATE_FORMAT.parse(consultationDate)
            val consultationTime = LocalTime.parse(consultationTime)
            ExamInfo(
                date = consultationDate.atTime(consultationTime),
                room = consultationRoom
            )
        } catch (e: IllegalArgumentException) {
            println(e.message)
            null
        }
    }

    fun toSubjectListItem(subjectsWithMoodleIds: List<String>): SubjectListItem =
        SubjectListItem(
            id = id.toString() + if (isDebt) DEBT_POSTFIX else NO_DEBT_POSTFIX,
            scienceId = scienceId.toString(),
            name = name,
            currentPoints = currentPoints,
            maxPoints = maxPoints.toString(),

            moodleCourseUrl = if (id.toString() in subjectsWithMoodleIds) {
                "https://orioks.miet.ru/mdl-gateway/course?science_id=$scienceId"
            } else {
                null
            },
            subjectInfo = SubjectInfo(
                formOfControl = formOfControl.toDisplayFormOfControl(),
                examInfo = getExamInfo(),
                consultationInfo = getConsultationInfo(),
                teachers = teachers.map { it.toDisplayTeacher() }
            ),
        )
}
