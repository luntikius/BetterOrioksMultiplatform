package model

import kotlinx.serialization.SerialName
@kotlinx.serialization.Serializable
data class Group(
    @SerialName("Code")
    val code: String,
    @SerialName("Name")
    val name: String
)