package data

import data.database.ScheduleDao
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate
import model.database.ScheduleDbEntities
import model.schedule.Schedule
import model.schedule.ScheduleClass
import model.schedule.ScheduleDay
import model.schedule.SwitchOptions
import utils.ScheduleUtils
import utils.ScheduleUtils.addGaps

class ScheduleDatabaseRepository(
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
                val daysClasses = elements[day.id]?.map { it.toScheduleClass() } ?: listOf()
                val daysElements = addGaps(daysClasses)
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

    suspend fun dumpAll() {
        scheduleDao.dumpSchedule()
        scheduleDao.dumpFirstOfTheMonths()
    }

    suspend fun insertNewSchedule(entities: ScheduleDbEntities) {
        dumpAll()
        scheduleDao.insertAllFirstOfTheMonths(entities.firstOfTheMonths)
        scheduleDao.insertAllWeeks(entities.scheduleWeeks)
        scheduleDao.insertAllDays(entities.scheduleDays)
        scheduleDao.insertAllElements(entities.scheduleElements)
    }

    suspend fun getSchedule(): Schedule {
        return scheduleFlow.first()
    }

    suspend fun isScheduleStored(): Boolean {
        return scheduleDao.countEntities() > 0
    }

    suspend fun recalculateWindows(element: ScheduleClass, switchOptions: SwitchOptions) {
        val (fromTime, toTime) = ScheduleUtils.getSwitchedTime(element.fromTime.toString(), element.toTime.toString())
        scheduleDao.updateWindows(
            day = element.day,
            number = element.number,
            subject = element.subject,
            fromTime = fromTime,
            toTime = toTime,
            dayCount = switchOptions.dayCount
        )
    }
}
