package model.subjects

data class SubjectListItem(
    val id: String,
    val scienceId: String,
    val name: String,
    override val currentPoints: String,
    override val maxPoints: String,
    val formOfControl: DisplayFormOfControl,
    val examInfo: ExamInfo?,
    val consultationInfo: ExamInfo?,
    val moodleCourseUrl: String?,
    val teachers: List<DisplayTeacher>
) : PointsDisplay
