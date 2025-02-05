package model.schedule

import androidx.compose.runtime.Composable
import betterorioks.composeapp.generated.resources.Res
import betterorioks.composeapp.generated.resources.gap_hours
import betterorioks.composeapp.generated.resources.gap_hours_with_minutes
import betterorioks.composeapp.generated.resources.gap_minutes
import kotlinx.datetime.LocalTime
import org.jetbrains.compose.resources.pluralStringResource

interface ScheduleElement {
    val day: Int
    val number: Int
}

data class ScheduleClass(
    override val day: Int = 0,
    override val number: Int = 0,
    val fromTime: LocalTime = LocalTime(11, 11),
    val toTime: LocalTime = LocalTime(12, 46),
    val type: String = "Пара",
    val subject: String = "Название предмета",
    val teacher: String = "Учитель",
    val room: String = "",
    val isSwitchable: Boolean = false
) : ScheduleElement

data class ScheduleGap(
    override val day: Int = 0,
    override val number: Int = 0,
    val gapDuration: Int = 0
) : ScheduleElement {

    @Composable
    fun getGapDurationString(): String {
        val hours = gapDuration / 60
        val minutes = gapDuration % 60
        return if (hours == 0) {
            pluralStringResource(Res.plurals.gap_minutes, minutes, minutes)
        } else if (minutes == 0) {
            pluralStringResource(Res.plurals.gap_hours, hours, hours)
        } else {
            pluralStringResource(Res.plurals.gap_hours_with_minutes, minutes, hours, minutes)
        }
    }

}
