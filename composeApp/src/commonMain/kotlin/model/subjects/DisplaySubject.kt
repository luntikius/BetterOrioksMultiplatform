package model.subjects

data class DisplaySubject(
    val id: String,
    val name: String,
    override val currentPoints: String,
    override val maxPoints: String,
    val formOfControl: Int,
) : PointsDisplay {
    companion object {
        const val FORM_OF_CONTROL_CREDIT = 1
    }
}
