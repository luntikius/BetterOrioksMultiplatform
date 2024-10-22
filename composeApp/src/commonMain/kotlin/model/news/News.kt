package model.news

import kotlinx.datetime.LocalDateTime

data class News(
    val title: String,
    val date: LocalDateTime,
    val id: String
)
