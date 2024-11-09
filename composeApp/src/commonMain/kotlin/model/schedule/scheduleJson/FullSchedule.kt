package model.schedule.scheduleJson

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.plus
import kotlinx.serialization.SerialName
import model.database.FirstOfTheMonthEntity
import model.database.ScheduleDayEntity
import model.database.ScheduleDbEntities
import model.database.ScheduleWeekEntity
import model.schedule.WeekType

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

        val visitedMonths = mutableSetOf<Month>()
        val firstOfTheMonths = mutableListOf<FirstOfTheMonthEntity>()
        val daysCount = SCHEDULE_WEEKS_COUNT * DAYS_IN_WEEK
        val daysOffset = getMondayOffset(semesterStartDate)
        val days = buildList {
            repeat(daysCount) { index ->
                val id = index + 1
                val weekId = index / DAYS_IN_WEEK + 1
                val date = semesterStartDate.plus(index - daysOffset, DateTimeUnit.DAY)
                if (date.month !in visitedMonths) {
                    firstOfTheMonths.add(FirstOfTheMonthEntity(date = date.toString()))
                    visitedMonths.add(date.month)
                }
                add(
                    ScheduleDayEntity(
                        id = id,
                        weekId = weekId,
                        date = date.toString()
                    )
                )
            }
        }

        val elements = buildList {
            repeat(daysCount) { index ->
                if (index >= daysOffset) {
                    val dayId = index + 1
                    val dayOfTheWeek = dayId % DAYS_IN_WEEK
                    val weekType = (dayId / DAYS_IN_WEEK) % 4
                    val filteredSubjects =
                        schedule.filter { it.day == dayOfTheWeek && it.dayNumber == weekType }
                            .sortedBy { it.time.dayOrder }
                    addAll(filteredSubjects.map { it.toScheduleElementEntity(dayId) })
                }
            }
        }

        return ScheduleDbEntities(
            firstOfTheMonths,
            weeks,
            days,
            elements
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
