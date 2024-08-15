package ui.scheduleScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import betterorioks.composeapp.generated.resources.Res
import betterorioks.composeapp.generated.resources.change_lesson_time
import betterorioks.composeapp.generated.resources.gap_minutes
import betterorioks.composeapp.generated.resources.room_number
import betterorioks.composeapp.generated.resources.scheldule
import betterorioks.composeapp.generated.resources.swap_vert
import betterorioks.composeapp.generated.resources.week_number
import kotlinx.datetime.LocalDate
import model.ScheduleClass
import model.ScheduleDay
import model.ScheduleElement
import model.ScheduleGap
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.common.MediumSpacer
import ui.common.SmallSpacer
import utils.getWeekStringRes

private const val MIN_SCHEDULE_ITEM_HEIGHT = 72

@Composable
fun WeekInfoRow(
    scheduleDay: ScheduleDay
) {
    Row(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            stringResource(scheduleDay.weekType.stringRes),
            fontSize = 14.sp,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f)
        )
        Text("", modifier = Modifier.padding(horizontal = 8.dp))
        Text(
            stringResource(Res.string.week_number, scheduleDay.weekNumber),
            fontSize = 14.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun DatePicker(
    viewModel: ScheduleScreenViewModel,
    uiState: ScheduleScreenUiState,
    lazyRowState: LazyListState,
    modifier: Modifier = Modifier
) {
    val schedule = uiState.schedule

    LazyRow(
        modifier = modifier,
        state = lazyRowState,
        horizontalArrangement = Arrangement.Center
    ) {
        items(schedule) { day ->
            DatePickerElement(
                date = day.date,
                isSelected = uiState.selectedDate == day.date,
                modifier = Modifier.fillParentMaxWidth(1 / 7f),
                onClick = { viewModel.selectDate(day.date) }
            )
        }
    }
}

@Composable
fun DatePickerElement(
    date: LocalDate,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    val dayOfWeekString = stringResource(date.getWeekStringRes())
    val circleColor = if (isSelected) {
        CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    } else {
        CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.background
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(start = 4.dp, end = 4.dp, bottom = 16.dp, top = 8.dp)
    ) {
        Text(text = dayOfWeekString)

        SmallSpacer()

        Card(
            shape = CircleShape,
            colors = circleColor,
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .aspectRatio(1f)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = date.dayOfMonth.toString(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier
                        .padding(8.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SchedulePager(
    schedule: List<ScheduleDay>,
    pagerState: PagerState,
    recalculateWindows: (number: Int, day: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    HorizontalPager(
        state = pagerState,
        modifier = modifier
    ) { page ->
        ScheduleColumn(
            scheduleList = schedule[page].scheduleList,
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
    LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
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
            if (scheduleClass.isSwitchable) {
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
            CircleText(text = scheduleClass.number.toString(), modifier = Modifier.size(26.dp))
            SmallSpacer()
            Text(
                text = scheduleClass.type,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier
                    .padding(4.dp)
                    .wrapContentSize(Alignment.Center)
            )
            Spacer(modifier = Modifier.weight(1f))
            CircleText(text = "${scheduleClass.fromTime} - ${scheduleClass.toTime}", modifier = Modifier.height(26.dp))
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
fun CircleText(text: String, modifier: Modifier = Modifier, color: Color = MaterialTheme.colorScheme.surfaceTint) {
    Surface(
        shape = CircleShape,
        modifier = modifier,
        color = color
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier
                .wrapContentSize(Alignment.Center)
                .padding(horizontal = 8.dp)
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
    val viewModel by remember { mutableStateOf(ScheduleScreenViewModel()) }
    val uiState = viewModel.uiState.collectAsState()
    val pagerState = rememberPagerState { uiState.value.schedule.size }
    val lazyRowState = rememberLazyListState()

    val schedule = uiState.value.schedule
    val selectedIndex = uiState.value.selectedDayIndex

    LaunchedEffect(pagerState.settledPage) {
        if (pagerState.settledPage != selectedIndex) viewModel.selectIndex(pagerState.settledPage)
    }

    LaunchedEffect(selectedIndex) {
        val day = uiState.value.selectedDate.dayOfWeek.ordinal
        val index = selectedIndex - day
        if (index in schedule.indices) {
            lazyRowState.animateScrollToItem(index)
        } else {
            lazyRowState.animateScrollToItem(0)
        }
        if (pagerState.settledPage != selectedIndex) pagerState.scrollToPage(selectedIndex)
    }

    Column {
        WeekInfoRow(
            uiState.value.selectedDay
        )
        DatePicker(
            viewModel,
            uiState.value,
            lazyRowState,
            modifier = Modifier.fillMaxWidth()
        )
        SmallSpacer()
        SchedulePager(
            uiState.value.schedule,
            pagerState,
            { _, _ -> }
        )
    }
}

@Composable
fun ScheduleScreen() {
    ScheduleItemsPreview()
}
