package model.subjects

data class SubjectListItem(
    val id: String,
    val scienceId: String,
    val name: String,
    override val currentPoints: String,
    override val maxPoints: String,
    val moodleCourseUrl: String?,
    val subjectInfo: SubjectInfo,
) : PointsDisplay
