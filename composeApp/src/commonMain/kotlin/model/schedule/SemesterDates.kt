package model.schedule

import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.char

data class SemesterDates(
    val startDate: String,
    val sessionStartDate: String?,
    val sessionEndDate: String?
) {
    companion object {
        val DATE_FORMAT = LocalDate.Format {
            dayOfMonth(); char('-'); monthNumber(); char('-'); year()
        }
    }
}
