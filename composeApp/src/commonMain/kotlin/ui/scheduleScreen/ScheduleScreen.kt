package ui.scheduleScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
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
import model.ScheduleWindow
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.pluralStringResource
import ui.common.SmallSpacer

private const val MIN_SCHEDULE_ITEM_HEIGHT = 72

@Composable
fun ScheduleItem(scheduleElement: ScheduleElement, recalculateWindows: (Int,Int) -> Unit){
    when (scheduleElement) {
        is ScheduleClass -> ClassItem(scheduleElement, recalculateWindows)
        is ScheduleWindow -> WindowItem(scheduleElement)
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
    scheduleWindow: ScheduleWindow,
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
            //tint = MaterialTheme.colors.primary,
            modifier = Modifier.size(32.dp)
        )
        SmallSpacer()
        Text(
            pluralStringResource(Res.plurals.window_minutes, scheduleWindow.windowDuration, scheduleWindow.windowDuration)
        )
    }
}