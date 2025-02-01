package model.news

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class News(
    @SerialName("id")
    val id: String,
    @SerialName("title")
    val title: String,
    @SerialName("date")
    val date: LocalDateTime,
)
