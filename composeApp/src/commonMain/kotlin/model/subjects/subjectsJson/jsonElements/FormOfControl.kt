package model.subjects.subjectsJson.jsonElements

import kotlinx.serialization.SerialName
import model.subjects.DisplayFormOfControl

@kotlinx.serialization.Serializable
data class FormOfControl(
    @SerialName("id")
    val id: Int = 0,
    @SerialName("name")
    val name: String = ""
) {
    fun toDisplayFormOfControl() = DisplayFormOfControl(
        id = id,
        name = name
    )
}
