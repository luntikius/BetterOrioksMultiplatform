package model.subjects.subjectsJson.jsonElements

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class ControlEvent(
    @SerialName("id")
    val id: Int = 0,
    @SerialName("name")
    val name: String = "",
    @SerialName("max_ball")
    val maxScore: Double = 0.0,
    @SerialName("sort")
    val sort: Int = 0,
    @SerialName("id_type")
    val idType: String = "",
    @SerialName("week")
    val week: Int = 0,
    @SerialName("num")
    val num: Int = 0,
    @SerialName("id_km")
    val idOfEvent: String = "",
    @SerialName("id_segment")
    val idSegment: Int = 0,
    @SerialName("date_start")
    val dateStart: String = "",
    @SerialName("date_end")
    val dateEnd: String = "",
    @SerialName("attempt")
    val attempt: String = "",
    @SerialName("sh")
    val shortName: String = "",
    @SerialName("type")
    val type: Type = Type(),
    @SerialName("grade")
    val grade: Grade = Grade(),
    @SerialName("irs")
    val resources: List<Resource> = listOf(),
    @SerialName("bonus")
    val bonus: Int = 0
)
