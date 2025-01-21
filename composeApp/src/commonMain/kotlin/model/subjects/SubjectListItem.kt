package model.subjects

data class SubjectListItem(
    val id: String,
    val scienceId: String,
    val name: String,
    override val currentPoints: String,
    override val maxPoints: String,
    val formOfControl: Int,
    val examInfo: ExamInfo?,
    val consultationInfo: ExamInfo?,
) : PointsDisplay {
    companion object {
        const val FORM_OF_CONTROL_CREDIT = 1
    }
}
