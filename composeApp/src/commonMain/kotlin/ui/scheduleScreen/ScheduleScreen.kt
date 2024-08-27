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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import betterorioks.composeapp.generated.resources.Res
import betterorioks.composeapp.generated.resources.arrow_drop_down
import betterorioks.composeapp.generated.resources.change_lesson_time
import betterorioks.composeapp.generated.resources.drop_down_menu
import betterorioks.composeapp.generated.resources.gap_minutes
import betterorioks.composeapp.generated.resources.loading_schedule
import betterorioks.composeapp.generated.resources.refresh_alert_text
import betterorioks.composeapp.generated.resources.room_number
import betterorioks.composeapp.generated.resources.schedule_scroll_to_today
import betterorioks.composeapp.generated.resources.scheldule
import betterorioks.composeapp.generated.resources.swap_vert
import betterorioks.composeapp.generated.resources.today
import betterorioks.composeapp.generated.resources.week_number
import kotlinx.datetime.LocalDate
import model.ScheduleClass
import model.ScheduleDay
import model.ScheduleElement
import model.ScheduleGap
import model.ScheduleWeek
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.resources.stringResource
import ui.common.LargeSpacer
import ui.common.LoadingScreen
import ui.common.MediumSpacer
import ui.common.RefreshAlert
import ui.common.SmallSpacer
import utils.getMonthStringRes
import utils.getShortMonthStringRes
import utils.getWeekStringRes

private const val MIN_SCHEDULE_ITEM_HEIGHT = 72

@Composable
fun MonthSelectorDropDown(
    isExpanded: Boolean,
    firstOfTheMonths: List<LocalDate>,
    onDateClick: (LocalDate) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    DropdownMenu(
        expanded = isExpanded,
        onDismissRequest = onDismiss,
        modifier = modifier
    ) {
        firstOfTheMonths.forEach {
            DropdownMenuItem(
                text = {
                    Text(
                        stringResource(it.getMonthStringRes()),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                onClick = {
                    onDateClick(it)
                    onDismiss()
                }
            )
        }
    }
}

@Composable
fun MonthInfoRow(
    viewModel: ScheduleScreenViewModel,
    uiState: ScheduleScreenUiState,
    modifier: Modifier = Modifier
) {
    val firstDate = uiState.selectedWeek.days.first().date
    val lastDate = uiState.selectedWeek.days.last().date
    val isWeekInOneMonth = firstDate.month == lastDate.month
    val monthString = if (isWeekInOneMonth) {
        stringResource(firstDate.getMonthStringRes())
    } else {
        "${stringResource(firstDate.getShortMonthStringRes())} - ${stringResource(lastDate.getShortMonthStringRes())}"
    }

    var isMonthSelectorVisible by remember { mutableStateOf(false) }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        MonthSelectorDropDown(
            isExpanded = isMonthSelectorVisible,
            firstOfTheMonths = uiState.schedule.firstOfTheMonths,
            onDateClick = viewModel::selectDayByDate,
            onDismiss = { isMonthSelectorVisible = false }
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(4.dp)
                .clip(RoundedCornerShape(8.dp))
                .clickable { isMonthSelectorVisible = true }
        ) {
            Text(
                monthString,
                style = MaterialTheme.typography.headlineSmall
            )
            SmallSpacer()
            Icon(
                painterResource(Res.drawable.arrow_drop_down),
                contentDescription = stringResource(Res.string.drop_down_menu)
            )
        }
        Spacer(Modifier.weight(1f))
        IconButton(
            onClick = { viewModel.selectToday() }
        ) {
            Icon(
                painterResource(Res.drawable.today),
                contentDescription = stringResource(Res.string.schedule_scroll_to_today),
                modifier = Modifier.size(28.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun WeekInfoRow(
    scheduleWeek: ScheduleWeek,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            stringResource(scheduleWeek.type.stringRes),
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.weight(1f)
        )
        Text("", modifier = Modifier.padding(horizontal = 8.dp))
        Text(
            stringResource(Res.string.week_number, scheduleWeek.number),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.weight(1f)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DatePicker(
    viewModel: ScheduleScreenViewModel,
    uiState: ScheduleScreenUiState,
    pagerState: PagerState,
    modifier: Modifier = Modifier
) {
    val weeks = uiState.weeks

    HorizontalPager(
        state = pagerState,
        modifier = modifier
    ) { weekIndex ->
        DatePickerWeek(
            week = weeks[weekIndex],
            uiState = uiState,
            viewModel = viewModel,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun DatePickerWeek(
    week: ScheduleWeek,
    uiState: ScheduleScreenUiState,
    viewModel: ScheduleScreenViewModel,
    modifier: Modifier = Modifier
) {
    val days = week.days
    Row(
        modifier = modifier
    ) {
        repeat(days.size) {
            val day = days[it]
            DatePickerElement(
                date = day.date,
                isSelected = uiState.selectedDay.date == day.date,
                modifier = Modifier.weight(1F),
                onClick = { viewModel.selectDay(day) }
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
@Composable
fun LaunchedTracker(
    viewModel: ScheduleScreenViewModel,
    uiState: State<ScheduleScreenUiState>,
    dayPagerState: PagerState,
    weekPagerState: PagerState
) {
    val selectedIndex = uiState.value.selectedDayIndex
    val selectedWeekIndex = uiState.value.selectedWeekIndex

    LaunchedEffect(dayPagerState.currentPage) {
        if (dayPagerState.currentPage != selectedIndex) viewModel.selectDayByIndex(dayPagerState.currentPage)
    }

    LaunchedEffect(weekPagerState.currentPage) {
        if (weekPagerState.currentPage != selectedWeekIndex) viewModel.selectWeekByIndex(weekPagerState.currentPage)
    }

    LaunchedEffect(selectedIndex) {
        if (weekPagerState.currentPage != selectedWeekIndex) {
            weekPagerState.animateScrollToPage(selectedWeekIndex)
        }
        if (dayPagerState.currentPage != selectedIndex) {
            dayPagerState.scrollToPage(selectedIndex)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ScheduleBox(
    viewModel: ScheduleScreenViewModel,
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.uiState.collectAsState()
    var isRefreshAlertVisible by remember { mutableStateOf(false) }
    val dayPagerState = rememberPagerState { uiState.value.days.size }
    val weekPagerState = rememberPagerState { uiState.value.weeks.size }
    val pullToRefreshState = rememberPullToRefreshState()

    LaunchedEffect(Unit) {
        dayPagerState.scrollToPage(uiState.value.selectedDayIndex)
        weekPagerState.scrollToPage(uiState.value.selectedWeekIndex)
    }

    LaunchedEffect(pullToRefreshState.isRefreshing) {
        if (pullToRefreshState.isRefreshing) {
            isRefreshAlertVisible = true
            pullToRefreshState.endRefresh()
        }
    }

    LaunchedTracker(viewModel, uiState, dayPagerState, weekPagerState)

    Box(
        modifier = modifier.nestedScroll(pullToRefreshState.nestedScrollConnection)
    ) {
        Column {
            MediumSpacer()
            MonthInfoRow(
                viewModel,
                uiState.value,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            MediumSpacer()
            WeekInfoRow(
                uiState.value.selectedWeek,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            DatePicker(
                viewModel,
                uiState.value,
                weekPagerState,
                modifier = Modifier.fillMaxWidth()
            )
            SmallSpacer()
            SchedulePager(
                uiState.value.days,
                dayPagerState,
                { _, _ -> }
            )
            LargeSpacer()
        }

        PullToRefreshContainer(
            modifier = Modifier.align(Alignment.TopCenter),
            state = pullToRefreshState,
            contentColor = MaterialTheme.colorScheme.primary,
            containerColor = MaterialTheme.colorScheme.surfaceTint
        )

        RefreshAlert(
            isVisible = isRefreshAlertVisible,
            text = stringResource(Res.string.refresh_alert_text),
            onRefresh = { viewModel.refreshSchedule() },
            onDismiss = { isRefreshAlertVisible = false },
        )
    }
}

@Composable
fun ScheduleScreen(
    viewModel: ScheduleScreenViewModel = remember { mutableStateOf(ScheduleScreenViewModel()) }.value
) {
    val isInitialized by viewModel.isInitialized.collectAsState()
    if (!isInitialized) {
        LoadingScreen(Modifier.fillMaxSize(), text = stringResource(Res.string.loading_schedule))
    } else {
        ScheduleBox(viewModel)
    }
}
