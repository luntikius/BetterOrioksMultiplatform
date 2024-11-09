package model.schedule.scheduleJson

import kotlinx.serialization.SerialName
import model.database.ScheduleElementEntity

@kotlinx.serialization.Serializable
data class ScheduleFromSiteElement(
    @SerialName("Day")
    val day: Int,
    @SerialName("DayNumber")
    val dayNumber: Int,
    @SerialName("Time")
    val time: TimeTableFromSiteElement,
    @SerialName("Class")
    val subject: ScheduleFromSiteSubject,
    @SerialName("Group")
    val group: Group,
    @SerialName("Room")
    val room: Room
) {
    fun toScheduleElementEntity(dayId: Int): ScheduleElementEntity {
        return ScheduleElementEntity(
            dayId = dayId,
            number = time.dayOrder,
            fromTime = time.start,
            toTime = time.end,
            type = subject.formFromString,
            subject = subject.nameFromString,
            teacher = subject.teacherFull,
            room = room.name
        )
    }
}
