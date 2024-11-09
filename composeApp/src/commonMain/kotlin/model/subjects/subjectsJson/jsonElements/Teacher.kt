package model.subjects.subjectsJson.jsonElements

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class Teacher(
    @SerialName("login")
    val login: String = "",
    @SerialName("name")
    val name: String = "",
    @SerialName("email")
    val email: String = ""
)
