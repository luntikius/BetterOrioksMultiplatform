package model.schedule.scheduleJson

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
class TimeTableFromSiteElement(
    @SerialName("Time")
    val time: String,
    @SerialName("Code")
    val code: Int,
    @SerialName("TimeFrom")
    val timeFrom: String,
    @SerialName("TimeTo")
    val timeTo: String
) {
    val start = timeFrom.slice(timeFrom.indexOf("T") + 1..timeFrom.indexOf("T") + 5)
    val end = timeTo.slice(timeTo.indexOf("T") + 1..timeTo.indexOf("T") + 5)
    val dayOrder = time.filter { it in "0123456789" }.toInt()
}
