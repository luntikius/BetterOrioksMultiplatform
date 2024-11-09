package model.subjects.subjectsJson.jsonElements

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class Segment(
    @SerialName("allKms")
    val allControlEvents: List<ControlEvent> = listOf()
)
