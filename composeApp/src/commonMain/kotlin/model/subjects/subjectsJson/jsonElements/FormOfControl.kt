package model.subjects.subjectsJson.jsonElements

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class FormOfControl(
    @SerialName("id")
    val id: Int = 0,
    @SerialName("name")
    val name: String = ""
)
