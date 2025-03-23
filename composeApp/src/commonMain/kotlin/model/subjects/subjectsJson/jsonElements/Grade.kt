package model.subjects.subjectsJson.jsonElements

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class Grade(
    @SerialName("b")
    val currentPoints: String = "-",
    @SerialName("f")
    val fullPoints: String = "-",
    @SerialName("p")
    val percent: String = "",
    @SerialName("o")
    val mark: String = "",
    @SerialName("w")
    val words: String = ""
)
