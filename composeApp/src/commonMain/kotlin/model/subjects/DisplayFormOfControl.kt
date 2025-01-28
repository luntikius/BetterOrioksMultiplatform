package model.subjects

data class DisplayFormOfControl(
    val id: Int,
    val name: String,
) {
    val isCredit: Boolean
        get() = this.id == FORM_OF_CONTROL_CREDIT

    companion object {
        const val FORM_OF_CONTROL_CREDIT = 1
    }
}
