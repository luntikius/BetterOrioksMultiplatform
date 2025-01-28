package model.subjectPerformance

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import betterorioks.composeapp.generated.resources.Res
import betterorioks.composeapp.generated.resources.weeks_before_event
import betterorioks.composeapp.generated.resources.weeks_before_event_now
import betterorioks.composeapp.generated.resources.weeks_before_event_one
import betterorioks.composeapp.generated.resources.weeks_before_event_passed
import model.subjects.PointsDisplay
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.resources.stringResource

sealed interface ControlEventsListItem {

    data class ControlEventItem(
        val id: String,
        val name: String,
        val description: String?,
        val shortName: String,
        override val currentPoints: String,
        override val maxPoints: String,
        val resources: List<DisplayResource>,
        val isBonus: Boolean
    ) : PointsDisplay, ControlEventsListItem {

        val fullName: String
            get() = "$name $shortName"

        @Composable
        override fun getPointsColor(): Color =
            if (isBonus) MaterialTheme.colorScheme.primary else super.getPointsColor()
    }

    data class WeeksLeftItem(
        val weeksLeft: Int
    ) : ControlEventsListItem {

        @Composable
        fun getWeeksLeftString(): String {
            return when (weeksLeft) {
                in Int.MIN_VALUE..<0 -> stringResource(Res.string.weeks_before_event_passed)
                0 -> stringResource(Res.string.weeks_before_event_now)
                1 -> stringResource(Res.string.weeks_before_event_one)
                else -> pluralStringResource(Res.plurals.weeks_before_event, weeksLeft - 1, weeksLeft - 1)
            }
        }
    }
}
