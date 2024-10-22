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
import betterorioks.composeapp.generated.resources.april_short
import betterorioks.composeapp.generated.resources.august_short
import betterorioks.composeapp.generated.resources.december_short
import betterorioks.composeapp.generated.resources.february_short
import betterorioks.composeapp.generated.resources.january_short
import betterorioks.composeapp.generated.resources.july_short
import betterorioks.composeapp.generated.resources.june_short
import betterorioks.composeapp.generated.resources.march_short
import betterorioks.composeapp.generated.resources.may_short
import betterorioks.composeapp.generated.resources.november_short
import betterorioks.composeapp.generated.resources.october_short
import betterorioks.composeapp.generated.resources.september_short
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import kotlinx.datetime.format.char
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

fun LocalDate.getShortMonthStringRes(): StringResource {
    return when (this.month) {
        Month.JANUARY -> Res.string.january_short
        Month.FEBRUARY -> Res.string.february_short
        Month.MARCH -> Res.string.march_short
        Month.APRIL -> Res.string.april_short
        Month.MAY -> Res.string.may_short
        Month.JUNE -> Res.string.june_short
        Month.JULY -> Res.string.july_short
        Month.AUGUST -> Res.string.august_short
        Month.SEPTEMBER -> Res.string.september_short
        Month.OCTOBER -> Res.string.october_short
        Month.NOVEMBER -> Res.string.november_short
        else -> Res.string.december_short
    }
}

object BetterOrioksFormats {
    val NEWS_DATE_TIME_FORMAT = LocalDateTime.Format {
        dayOfMonth(); char('.'); monthNumber(); char('.'); year()
        char(' '); hour(); char(':'); minute()
    }
}
