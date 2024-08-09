package ui.scheduleScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import betterorioks.composeapp.generated.resources.Res
import betterorioks.composeapp.generated.resources.change_lesson_time
import betterorioks.composeapp.generated.resources.gap_minutes
import betterorioks.composeapp.generated.resources.room_number
import betterorioks.composeapp.generated.resources.scheldule
import betterorioks.composeapp.generated.resources.swap_vert
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SchedulePager(
    schedule: List<List<ScheduleElement>>,
    pagerState: PagerState,
    recalculateWindows: (number: Int, day: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    HorizontalPager(
        state = pagerState,
        modifier = modifier
    ) { page ->
        ScheduleColumn(
            scheduleList = schedule[page],
            recalculateWindows = recalculateWindows
        )
    }
}

@Composable
fun ScheduleColumn(
    scheduleList: List<ScheduleElement>,
    recalculateWindows: (number: Int, day: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        items(scheduleList) {
            ScheduleItem(
                scheduleElement = it,
                recalculateWindows = { recalculateWindows(it.number, it.day) }
            )
        }
    }
}

@Composable
fun ScheduleItem(scheduleElement: ScheduleElement, recalculateWindows: () -> Unit) {
    when (scheduleElement) {
        is ScheduleClass -> ClassItem(scheduleElement, recalculateWindows)
        is ScheduleGap -> GapItem(scheduleElement)
        else -> throw IllegalArgumentException("There is no such ScheduleElement")
    }
}

@Composable
fun ClassItem(
    scheduleClass: ScheduleClass,
    recalculateWindows: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shouldDisplaySwitchButton = scheduleClass.number == CHANGEABLE

    ElevatedCard(
        colors = CardDefaults.elevatedCardColors(),
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp)
            .defaultMinSize(minHeight = MIN_SCHEDULE_ITEM_HEIGHT.dp)
    ) {
        Box {
            ClassItemContent(scheduleClass)
            if (shouldDisplaySwitchButton) {
                SwitchButton(
                    recalculateWindows,
                    modifier = Modifier.align(Alignment.BottomEnd)
                )
            }
        }
    }
}

@Composable
fun ClassItemContent(
    scheduleClass: ScheduleClass,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
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

@Composable
fun SwitchButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .padding(4.dp)
    ) {
        Icon(
            painterResource(Res.drawable.swap_vert),
            contentDescription = stringResource(Res.string.change_lesson_time),
            modifier = Modifier.size(28.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
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
            pluralStringResource(Res.plurals.gap_minutes, scheduleGap.gapDuration, scheduleGap.gapDuration)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun ScheduleItemsPreview() {
    val viewModel = ScheduleScreenViewModel()
    val uiState = viewModel.uiState.collectAsState()
    val pagerState = rememberPagerState { uiState.value.schedule.size }
    SchedulePager(
        uiState.value.schedule,
        pagerState,
        { _, _ -> }
    )
}

@Composable
fun ScheduleScreen() {
    ScheduleItemsPreview()
}
