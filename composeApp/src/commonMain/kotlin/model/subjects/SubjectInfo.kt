package model.subjects

data class SubjectInfo(
    val examInfo: ExamInfo?,
    val consultationInfo: ExamInfo?,
    val formOfControl: DisplayFormOfControl,
    val teachers: List<DisplayTeacher>,
)
