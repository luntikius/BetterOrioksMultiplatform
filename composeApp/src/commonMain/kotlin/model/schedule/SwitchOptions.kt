package model.schedule

import betterorioks.composeapp.generated.resources.Res
import betterorioks.composeapp.generated.resources.switch_options_every_four_weeks
import betterorioks.composeapp.generated.resources.switch_options_every_two_weeks
import betterorioks.composeapp.generated.resources.switch_options_every_week
import org.jetbrains.compose.resources.StringResource

enum class SwitchOptions(val nameRes: StringResource, val dayCount: Int) {
    SWITCH_EVERY_WEEK(
        nameRes = Res.string.switch_options_every_week,
        dayCount = 7
    ),
    SWITCH_EVERY_TWO_WEEKS(
        nameRes = Res.string.switch_options_every_two_weeks,
        dayCount = 14
    ),
    SWITCH_EVERY_FOUR_WEEKS(
        nameRes = Res.string.switch_options_every_four_weeks,
        dayCount = 28
    )
}
