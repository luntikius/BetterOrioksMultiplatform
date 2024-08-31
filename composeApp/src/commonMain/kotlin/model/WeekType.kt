package model

import betterorioks.composeapp.generated.resources.Res
import betterorioks.composeapp.generated.resources.first_higher
import betterorioks.composeapp.generated.resources.first_lower
import betterorioks.composeapp.generated.resources.second_higher
import betterorioks.composeapp.generated.resources.second_lower
import org.jetbrains.compose.resources.StringResource

enum class WeekType(
    val stringRes: StringResource
) {
    FIRST_HIGHER(stringRes = Res.string.first_higher),
    FIRST_LOWER(stringRes = Res.string.first_lower),
    SECOND_HIGHER(stringRes = Res.string.second_higher),
    SECOND_LOWER(stringRes = Res.string.second_lower)
}
