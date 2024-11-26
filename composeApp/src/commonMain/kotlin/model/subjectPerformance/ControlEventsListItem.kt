package model.subjectPerformance

import androidx.compose.runtime.Composable
import betterorioks.composeapp.generated.resources.Res
import betterorioks.composeapp.generated.resources.weeks_before_event
import betterorioks.composeapp.generated.resources.weeks_before_event_now
import betterorioks.composeapp.generated.resources.weeks_before_event_passed
import model.subjects.PointsDisplay
import model.subjects.subjectsJson.jsonElements.Resource
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.resources.stringResource

sealed interface ControlEventsListItem {

    data class ControlEventItem(
        val id: String,
        val name: String,
        val shortName: String,
        override val currentPoints: String,
        override val maxPoints: String,
        val resources: List<Resource>,
        val isBonus: Boolean
    ) : PointsDisplay, ControlEventsListItem

    data class WeeksLeftItem(
        val weeksLeft: Int
    ) : ControlEventsListItem {

        @Composable
        fun getWeeksLeftString(): String {
            return when (weeksLeft) {
                in Int.MIN_VALUE..<0 -> stringResource(Res.string.weeks_before_event_passed)
                0 -> stringResource(Res.string.weeks_before_event_now)
                else -> pluralStringResource(Res.plurals.weeks_before_event, weeksLeft, weeksLeft)
            }
        }
    }
}
