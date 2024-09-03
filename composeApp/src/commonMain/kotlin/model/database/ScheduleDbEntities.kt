package model.database

data class ScheduleDbEntities(
    val firstOfTheMonths: List<FirstOfTheMonthEntity>,
    val scheduleWeeks: List<ScheduleWeekEntity>,
    val scheduleDays: List<ScheduleDayEntity>,
    val scheduleElements: List<ScheduleElementEntity>
)
