package model.subjects

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format
import utils.BetterOrioksFormats

data class ExamInfo(
    val date: LocalDateTime,
    val room: String,
) {
    val dateString = date.format(BetterOrioksFormats.NEWS_DATE_TIME_FORMAT)
}
