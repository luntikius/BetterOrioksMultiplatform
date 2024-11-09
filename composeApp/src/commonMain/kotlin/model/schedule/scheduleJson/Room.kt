package model.schedule.scheduleJson

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class Room(
    @SerialName("Code")
    val code: Int,
    @SerialName("Name")
    val name: String
)
