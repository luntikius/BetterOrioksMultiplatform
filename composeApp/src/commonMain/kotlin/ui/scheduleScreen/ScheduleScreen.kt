package ui.scheduleScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import betterorioks.composeapp.generated.resources.Res
import betterorioks.composeapp.generated.resources.scheldule
import betterorioks.composeapp.generated.resources.window_minutes
import model.ScheduleClass
import model.ScheduleElement
import model.ScheduleGap
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.common.SmallSpacer

private const val MIN_SCHEDULE_ITEM_HEIGHT = 72

@Composable
fun ScheduleItem(scheduleElement: ScheduleElement, recalculateWindows: (Int,Int) -> Unit){
    when (scheduleElement) {
        is ScheduleClass -> ClassItem(scheduleElement, recalculateWindows)
        is ScheduleGap -> WindowItem(scheduleElement)
        else -> throw IllegalArgumentException()
    }
}

@Composable
fun ClassItem(
    scheduleClass: ScheduleClass,
    recalculateWindows: (Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {

}

@Composable
fun WindowItem(
    scheduleGap: ScheduleGap,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .defaultMinSize(minHeight = MIN_SCHEDULE_ITEM_HEIGHT.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(Res.drawable.scheldule),
            contentDescription = null,
            tint = MaterialTheme.colors.primary,
            modifier = Modifier.size(32.dp)
        )
        SmallSpacer()
        Text(
            pluralStringResource(Res.plurals.window_minutes, scheduleGap.windowDuration, scheduleGap.windowDuration)
        )
    }
}

@Preview
@Composable
fun WindowItemsPreview() {
    Column {
        WindowItem(
            scheduleGap = ScheduleGap(windowDuration = 1)
        )
        WindowItem(
            scheduleGap = ScheduleGap(windowDuration = 2)
        )
        WindowItem(
            scheduleGap = ScheduleGap(windowDuration = 3)
        )
        WindowItem(
            scheduleGap = ScheduleGap(windowDuration = 5)
        )
        WindowItem(
            scheduleGap = ScheduleGap(windowDuration = 11)
        )
        WindowItem(
            scheduleGap = ScheduleGap(windowDuration = 31)
        )
    }
}

@Composable
fun ScheduleScreen() {
    WindowItemsPreview()
}