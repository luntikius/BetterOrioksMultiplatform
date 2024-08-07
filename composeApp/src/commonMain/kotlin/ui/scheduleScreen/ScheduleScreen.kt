package ui.scheduleScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import betterorioks.composeapp.generated.resources.Res
import betterorioks.composeapp.generated.resources.gap_minutes
import betterorioks.composeapp.generated.resources.room_number
import betterorioks.composeapp.generated.resources.scheldule
import model.ScheduleClass
import model.ScheduleElement
import model.ScheduleGap
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.common.MediumSpacer
import ui.common.SmallSpacer

private const val MIN_SCHEDULE_ITEM_HEIGHT = 72
private const val CHANGEABLE = 3

@Composable
fun ScheduleItem(scheduleElement: ScheduleElement, recalculateWindows: (Int,Int) -> Unit){
    when (scheduleElement) {
        is ScheduleClass -> ClassItem(scheduleElement, recalculateWindows)
        is ScheduleGap -> GapItem(scheduleElement)
        else -> throw IllegalArgumentException()
    }
}

@Composable
fun ClassItem(
    scheduleClass: ScheduleClass,
    recalculateWindows: (Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        colors = CardDefaults.elevatedCardColors(),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp)
            .defaultMinSize(minHeight = MIN_SCHEDULE_ITEM_HEIGHT.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircleNumber(scheduleClass.number)
                SmallSpacer()
                Text(
                    text = scheduleClass.type,
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier
                        .padding(4.dp)
                        .wrapContentSize(Alignment.Center)
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "${scheduleClass.fromTime} - ${scheduleClass.toTime}")
            }
            MediumSpacer()
            Text(
                text = scheduleClass.subject,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Text(
                text = scheduleClass.teacher,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            MediumSpacer()
            Text(
                text = stringResource(Res.string.room_number, scheduleClass.room),
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            MediumSpacer()
        }
    }
}

@Composable
fun CircleNumber(number: Int, modifier: Modifier = Modifier, color: Color = MaterialTheme.colorScheme.surfaceTint) {
    Surface(
        shape = CircleShape,
        modifier = modifier.size(26.dp),
        color = color
    ) {
        Text(
            text = number.toString(),
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier
                .wrapContentSize(Alignment.Center)
        )
    }
}

@Composable
fun GapItem(
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
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(32.dp)
        )
        MediumSpacer()
        Text(
            pluralStringResource(Res.plurals.gap_minutes, scheduleGap.windowDuration, scheduleGap.windowDuration)
        )
    }
}

@Preview
@Composable
fun ScheduleItemsPreview() {
    Column {
        GapItem(
            scheduleGap = ScheduleGap(windowDuration = 1)
        )
        ClassItem(
            scheduleClass = ScheduleClass(),
            recalculateWindows = {_,_ ->}
        )
        GapItem(
            scheduleGap = ScheduleGap(windowDuration = 2)
        )
        ClassItem(
            scheduleClass = ScheduleClass(),
            recalculateWindows = {_,_ ->}
        )
        GapItem(
            scheduleGap = ScheduleGap(windowDuration = 3)
        )
        ClassItem(
            scheduleClass = ScheduleClass(),
            recalculateWindows = {_,_ ->}
        )
        GapItem(
            scheduleGap = ScheduleGap(windowDuration = 5)
        )
        GapItem(
            scheduleGap = ScheduleGap(windowDuration = 11)
        )
        GapItem(
            scheduleGap = ScheduleGap(windowDuration = 31)
        )
    }
}

@Composable
fun ScheduleScreen() {
    ScheduleItemsPreview()
}