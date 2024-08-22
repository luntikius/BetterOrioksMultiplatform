package model

import kotlinx.serialization.SerialName
@kotlinx.serialization.Serializable
data class Classroom(
    @SerialName("Code")
    val code: Int,
    @SerialName("Name")
    val name: String
)