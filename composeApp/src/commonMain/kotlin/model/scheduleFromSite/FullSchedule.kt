package model.scheduleFromSite

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus
import kotlinx.serialization.SerialName
import model.WeekType
import model.database.FirstOfTheMonthEntity
import model.database.ScheduleDayEntity
import model.database.ScheduleDbEntities
import model.database.ScheduleWeekEntity

@kotlinx.serialization.Serializable
data class FullSchedule(
    @SerialName("Times")
    val timeTable: List<TimeTableFromSiteElement> = listOf(),
    @SerialName("Data")
    val schedule: List<ScheduleFromSiteElement> = listOf(),
    @SerialName("Semestr")
    val semester: String = ""
) {
    fun toScheduleDbEntities(semesterStartDate: LocalDate): ScheduleDbEntities {
        val weeks = buildList {
            repeat(SCHEDULE_WEEKS_COUNT) { index ->
                val id = index + 1
                val weekType = WeekType.entries[index % 4]
                add(ScheduleWeekEntity(id, weekType.toString()))
            }
        }

        val firstOfTheMonths = mutableListOf<FirstOfTheMonthEntity>()
        val daysCount = SCHEDULE_WEEKS_COUNT * DAYS_IN_WEEK
        val days = buildList {
            repeat(daysCount) { index ->
                val id = index + 1
                val weekId = index / DAYS_IN_WEEK + 1
                val date = semesterStartDate.plus(index, DateTimeUnit.DAY)
                if (date.dayOfMonth == 1) firstOfTheMonths.add(FirstOfTheMonthEntity(date = date.toString()))
                add(
                    ScheduleDayEntity(
                        id = id,
                        weekId = weekId,
                        date = date.toString()
                    )
                )
            }
        }

        val daysOffset = getMondayOffset(semesterStartDate)

        return ScheduleDbEntities(
            firstOfTheMonths,
            weeks,
            days,
            listOf()
        )
    }

    companion object {
        private const val DAYS_IN_WEEK = 7
        private const val SCHEDULE_WEEKS_COUNT = 20

        private fun getMondayOffset(date: LocalDate): Int {
            return date.dayOfWeek.ordinal
        }
    }
}
