package model.subjects

import kotlinx.datetime.LocalDateTime

data class ExamInfo(
    val date: LocalDateTime,
    val room: String,
)
