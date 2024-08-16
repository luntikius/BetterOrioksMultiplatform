package utils

import betterorioks.composeapp.generated.resources.APRIL
import betterorioks.composeapp.generated.resources.AUGUST
import betterorioks.composeapp.generated.resources.DECEMBER
import betterorioks.composeapp.generated.resources.FEBRUARY
import betterorioks.composeapp.generated.resources.FRIDAY
import betterorioks.composeapp.generated.resources.JANUARY
import betterorioks.composeapp.generated.resources.JULY
import betterorioks.composeapp.generated.resources.JUNE
import betterorioks.composeapp.generated.resources.MARCH
import betterorioks.composeapp.generated.resources.MAY
import betterorioks.composeapp.generated.resources.MONDAY
import betterorioks.composeapp.generated.resources.NOVEMBER
import betterorioks.composeapp.generated.resources.OCTOBER
import betterorioks.composeapp.generated.resources.Res
import betterorioks.composeapp.generated.resources.SATURDAY
import betterorioks.composeapp.generated.resources.SEPTEMBER
import betterorioks.composeapp.generated.resources.SUNDAY
import betterorioks.composeapp.generated.resources.THURSDAY
import betterorioks.composeapp.generated.resources.TUESDAY
import betterorioks.composeapp.generated.resources.WEDNESDAY
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
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

fun LocalDate.getMonthStringRes(): StringResource {
    return when (this.month) {
        Month.JANUARY -> Res.string.JANUARY
        Month.FEBRUARY -> Res.string.FEBRUARY
        Month.MARCH -> Res.string.MARCH
        Month.APRIL -> Res.string.APRIL
        Month.MAY -> Res.string.MAY
        Month.JUNE -> Res.string.JUNE
        Month.JULY -> Res.string.JULY
        Month.AUGUST -> Res.string.AUGUST
        Month.SEPTEMBER -> Res.string.SEPTEMBER
        Month.OCTOBER -> Res.string.OCTOBER
        Month.NOVEMBER -> Res.string.NOVEMBER
        else -> Res.string.DECEMBER
    }
}
