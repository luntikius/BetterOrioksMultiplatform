package utils

import betterorioks.composeapp.generated.resources.FRIDAY
import betterorioks.composeapp.generated.resources.MONDAY
import betterorioks.composeapp.generated.resources.Res
import betterorioks.composeapp.generated.resources.SATURDAY
import betterorioks.composeapp.generated.resources.SUNDAY
import betterorioks.composeapp.generated.resources.THURSDAY
import betterorioks.composeapp.generated.resources.TUESDAY
import betterorioks.composeapp.generated.resources.WEDNESDAY
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.StringResource

fun LocalDate.getWeekStringRes(): StringResource {
    return when (this.dayOfWeek) {
        DayOfWeek.MONDAY -> Res.string.MONDAY
        DayOfWeek.TUESDAY -> Res.string.TUESDAY
        DayOfWeek.WEDNESDAY -> Res.string.WEDNESDAY
        DayOfWeek.THURSDAY -> Res.string.THURSDAY
        DayOfWeek.FRIDAY -> Res.string.FRIDAY
        DayOfWeek.SATURDAY -> Res.string.SATURDAY
        else -> Res.string.SUNDAY
    }
}
