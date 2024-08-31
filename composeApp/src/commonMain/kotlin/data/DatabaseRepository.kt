package data

import database.ScheduleDao
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate
import model.Schedule
import model.ScheduleDay

class DatabaseRepository(
    private val scheduleDao: ScheduleDao
) {
    private val weeksFlow = scheduleDao.getWeeksFlow()
    private val daysFlow = scheduleDao.getScheduleDaysByWeeksFlow()
    private val elementsFlow = scheduleDao.getScheduleElementsByDaysFlow()
    private val firstOfTheMonthFlow = scheduleDao.getFirstOfTheMonthsFlow()
        .map { it.map { fotm -> LocalDate.parse(fotm.date) } }

    private val scheduleDaysFlow = daysFlow.combine(elementsFlow) { days, elements ->
        buildMap<Int, List<ScheduleDay>> {
            days.forEach { day ->
                val daysElements = elements[day.id]!!.map { it.toScheduleElement() }
                val scheduleDay = day.toScheduleDay(daysElements)
                if (this[day.weekId] == null) {
                    put(day.weekId, listOf(scheduleDay))
                } else {
                    this[day.weekId] = this[day.weekId]!! + listOf(scheduleDay)
                }
            }
        }
    }

    private val weeksListFlow = weeksFlow.combine(scheduleDaysFlow) { weeks, days ->
        buildList {
            weeks.forEach { week ->
                add(week.toScheduleWeek(days[week.id]!!))
            }
        }
    }

    private val scheduleFlow = weeksListFlow.combine(firstOfTheMonthFlow) { weeksList, firstOfTheMonths ->
        Schedule(weeksList, firstOfTheMonths)
    }

    suspend fun getSchedule(): Schedule {
        return scheduleFlow.first()
    }
}
