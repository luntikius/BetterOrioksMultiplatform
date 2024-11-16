package model.schedule

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.char
import kotlinx.datetime.periodUntil
import kotlinx.datetime.toLocalDateTime
import utils.toSemesterLocalDate

data class SemesterDates(
    val startDate: String,
    val sessionStartDate: String?,
    val sessionEndDate: String?
) {
    val weeksPassedSinceSemesterStart: Int
        get() {
            val start = startDate.toSemesterLocalDate()
            val current = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
            val weeks = start.periodUntil(current).days / 7
            return weeks
        }

    companion object {
        val DATE_FORMAT = LocalDate.Format {
            dayOfMonth(); char('-'); monthNumber(); char('-'); year()
        }
    }
}
