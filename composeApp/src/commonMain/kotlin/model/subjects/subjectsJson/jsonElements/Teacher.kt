package model.subjects.subjectsJson.jsonElements

import kotlinx.serialization.SerialName
import model.subjects.DisplayTeacher

@kotlinx.serialization.Serializable
data class Teacher(
    @SerialName("login")
    val login: String = "",
    @SerialName("name")
    val name: String = "",
    @SerialName("email")
    val email: String = ""
) {
    fun toDisplayTeacher() = DisplayTeacher(
        name = name,
        email = email
    )
}
