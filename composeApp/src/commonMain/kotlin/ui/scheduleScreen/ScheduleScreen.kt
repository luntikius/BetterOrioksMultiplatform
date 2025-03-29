package ui.scheduleScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import betterorioks.composeapp.generated.resources.Refresh
import betterorioks.composeapp.generated.resources.Res
import betterorioks.composeapp.generated.resources.arrow_drop_down
import betterorioks.composeapp.generated.resources.cat
import betterorioks.composeapp.generated.resources.change_lesson_time
import betterorioks.composeapp.generated.resources.clouds
import betterorioks.composeapp.generated.resources.drop_down_menu
import betterorioks.composeapp.generated.resources.free_day
import betterorioks.composeapp.generated.resources.gold_star
import betterorioks.composeapp.generated.resources.loading_schedule
import betterorioks.composeapp.generated.resources.loading_schedule_from_web
import betterorioks.composeapp.generated.resources.no_schedule
import betterorioks.composeapp.generated.resources.no_schedule_full
import betterorioks.composeapp.generated.resources.refresh
import betterorioks.composeapp.generated.resources.refresh_alert_text
import betterorioks.composeapp.generated.resources.reload
import betterorioks.composeapp.generated.resources.room_number
import betterorioks.composeapp.generated.resources.schedule
import betterorioks.composeapp.generated.resources.schedule_scroll_to_today
import betterorioks.composeapp.generated.resources.semester_end
import betterorioks.composeapp.generated.resources.swap_vert
import betterorioks.composeapp.generated.resources.today
import betterorioks.composeapp.generated.resources.week_number
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import model.schedule.ScheduleClass
import model.schedule.ScheduleDay
import model.schedule.ScheduleElement
import model.schedule.ScheduleGap
import model.schedule.ScheduleState
import model.schedule.ScheduleWeek
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.common.AttentionAlert
import ui.common.ErrorScreenWithReloadButton
import ui.common.LargeSpacer
import ui.common.LoadingScreen
import ui.common.MediumSpacer
import ui.common.SmallSpacer
import ui.common.SwipeRefreshBox
import ui.common.SwitchAlert
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
    val isEmptySchedule = uiState.weeks.isEmpty()
    val monthString = if (!isEmptySchedule) {
        val selectedWeek = uiState.weeks[uiState.selectedWeekIndex]
        val firstDate = selectedWeek.days.first().date
        val lastDate = selectedWeek.days.last().date
        val isWeekInOneMonth = firstDate.month == lastDate.month
        if (isWeekInOneMonth) {
            stringResource(firstDate.getMonthStringRes())
        } else {
            stringResource(firstDate.getShortMonthStringRes()) +
                " - " +
                stringResource(lastDate.getShortMonthStringRes())
        }
    } else {
        stringResource(Res.string.no_schedule)
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
                .clickable(!isEmptySchedule) { isMonthSelectorVisible = true }
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
            onClick = { viewModel.setRefreshPopupVisibility(true) },
        ) {
            Icon(
                painterResource(Res.drawable.refresh),
                contentDescription = stringResource(Res.string.reload),
                modifier = Modifier.size(28.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }
        SmallSpacer()
        IconButton(
            onClick = { viewModel.selectToday() },
            enabled = !isEmptySchedule
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
    uiState: ScheduleScreenUiState,
    modifier: Modifier = Modifier
) {
    val (type, number) = if (uiState.weeks.isNotEmpty()) {
        val week = uiState.weeks[uiState.selectedWeekIndex]
        listOf(
            stringResource(week.type.stringRes),
            stringResource(Res.string.week_number, week.number)
        )
    } else {
        listOf("...", "...")
    }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            type,
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
        Text("", modifier = Modifier.padding(horizontal = 8.dp))
        Text(
            number,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
    }
}

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
        userScrollEnabled = !uiState.isWeekAutoScrollInProgress,
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
                isSelected = uiState.days[uiState.selectedDayIndex].date == day.date,
                modifier = Modifier.weight(1F),
                onClick = { viewModel.selectDayByDate(day.date) },
                isEnabled = !(uiState.isDayAutoScrollInProgress || uiState.isWeekAutoScrollInProgress)
            )
        }
    }
}

@Composable
fun DatePickerElement(
    date: LocalDate,
    isSelected: Boolean,
    isEnabled: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    val dayOfWeekString = stringResource(date.getWeekStringRes())
    val circleColor = MaterialTheme.colorScheme.primary
    val textColor = if (isSelected) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onBackground
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clickable(onClick = onClick, enabled = isEnabled)
            .padding(start = 4.dp, end = 4.dp, bottom = 16.dp, top = 8.dp)
    ) {
        val additionalModifier = if (isSelected) {
            Modifier.drawBehind {
                drawCircle(
                    color = circleColor,
                    radius = 20.dp.toPx()
                )
            }
        } else {
            Modifier
        }
        Text(text = dayOfWeekString)
        SmallSpacer()
        Text(
            text = date.dayOfMonth.toString(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium.copy(color = textColor),
            modifier = Modifier
                .padding(8.dp)
                .then(additionalModifier)
        )
        SmallSpacer()
    }
}

@Composable
fun SchedulePager(
    schedule: List<ScheduleDay>,
    isUserScrollEnabled: Boolean,
    pagerState: PagerState,
    recalculateWindows: (element: ScheduleClass) -> Unit,
    modifier: Modifier = Modifier
) {
    if (schedule.isNotEmpty()) {
        HorizontalPager(
            state = pagerState,
            userScrollEnabled = isUserScrollEnabled,
            modifier = modifier
        ) { page ->
            ScheduleColumn(
                isLastPage = page == schedule.size - 1,
                scheduleList = schedule[page].scheduleList,
                recalculateWindows = recalculateWindows,
                modifier = Modifier.fillMaxSize()
            )
        }
    } else {
        EmptySchedule(modifier.fillMaxSize())
    }
}

@Composable
fun ScheduleColumn(
    isLastPage: Boolean,
    scheduleList: List<ScheduleElement>,
    recalculateWindows: (element: ScheduleClass) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.Top)) {
        if (scheduleList.isNotEmpty()) {
            items(scheduleList) {
                ScheduleItem(
                    scheduleElement = it,
                    recalculateWindows = recalculateWindows
                )
            }
            item { LargeSpacer() }
        } else {
            item { EmptyScheduleItem(isLastPage = isLastPage, modifier = Modifier.fillParentMaxHeight()) }
        }
    }
}

@Composable
fun EmptyScheduleItem(
    isLastPage: Boolean = false,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(16.dp).fillMaxWidth()
    ) {
        val string: String
        val image: Painter

        if (isLastPage) {
            string = stringResource(Res.string.semester_end)
            image = painterResource(Res.drawable.cat)
        } else {
            string = stringResource(Res.string.free_day)
            image = painterResource(Res.drawable.gold_star)
        }

        Spacer(Modifier.weight(1F))
        Image(image, contentDescription = null, modifier = Modifier.size(150.dp))
        LargeSpacer()
        Text(
            string,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.weight(2F))
    }
}

@Composable
fun ScheduleItem(scheduleElement: ScheduleElement, recalculateWindows: (element: ScheduleClass) -> Unit) {
    when (scheduleElement) {
        is ScheduleClass -> ClassItem(scheduleElement, recalculateWindows)
        is ScheduleGap -> GapItem(scheduleElement)
        else -> throw IllegalArgumentException("There is no such ScheduleElement")
    }
}

@Composable
fun ClassItem(
    scheduleClass: ScheduleClass,
    recalculateWindows: (element: ScheduleClass) -> Unit,
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
        ClassItemContent(scheduleClass, { recalculateWindows(scheduleClass) })
    }
}

@Composable
fun ClassItemContent(
    scheduleClass: ScheduleClass,
    onSwitchButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        MediumSpacer()
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp)
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
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        MediumSpacer()
        Row {
            Column(
                modifier = Modifier.weight(1F)
            ) {
                Text(
                    text = scheduleClass.teacher,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
                SmallSpacer()
                Text(
                    text = stringResource(Res.string.room_number, scheduleClass.room),
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
                LargeSpacer()
            }
            if (scheduleClass.isSwitchable) {
                SwitchButton(
                    onSwitchButtonClick,
                    modifier = Modifier.padding(bottom = 4.dp, end = 4.dp).align(Alignment.Bottom)
                )
            }
        }
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
fun CircleText(text: String, modifier: Modifier = Modifier, color: Color = MaterialTheme.colorScheme.background) {
    Surface(
        shape = CircleShape,
        modifier = modifier,
        color = color
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
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
            painter = painterResource(Res.drawable.schedule),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(32.dp)
        )
        MediumSpacer()
        Text(
            scheduleGap.getGapDurationString()
        )
    }
}

@Composable
fun LaunchedTracker(
    viewModel: ScheduleScreenViewModel,
    uiState: ScheduleScreenUiState,
    dayPagerState: PagerState,
    weekPagerState: PagerState
) {
    val selectedIndex = uiState.selectedDayIndex
    val selectedWeekIndex = uiState.selectedWeekIndex
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(dayPagerState.currentPage) {
        if (dayPagerState.currentPage != selectedIndex && !uiState.isDayAutoScrollInProgress) {
            viewModel.selectDayByIndex(dayPagerState.currentPage)
        }
    }

    LaunchedEffect(weekPagerState.currentPage) {
        if (weekPagerState.currentPage != selectedWeekIndex && !uiState.isWeekAutoScrollInProgress) {
            viewModel.selectWeekByIndex(weekPagerState.currentPage)
        }
    }

    LaunchedEffect(selectedWeekIndex) {
        if (weekPagerState.currentPage != selectedWeekIndex) {
            coroutineScope.launch {
                viewModel.setWeekAutoscroll(true)
                weekPagerState.animateScrollToPage(selectedWeekIndex)
                viewModel.setWeekAutoscroll(false)
            }
        }
    }

    LaunchedEffect(selectedIndex) {
        if (dayPagerState.currentPage != selectedIndex) {
            coroutineScope.launch {
                viewModel.setDayAutoscroll(true)
                dayPagerState.animateScrollToPage(selectedIndex)
                viewModel.setDayAutoscroll(false)
            }
        }
    }
}

@Composable
fun ScheduleBox(
    viewModel: ScheduleScreenViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    var isSwitchOptionsAlertVisible by remember { mutableStateOf(false) }
    val dayPagerState = rememberPagerState(initialPage = uiState.selectedDayIndex) { uiState.days.size }
    val weekPagerState = rememberPagerState(initialPage = uiState.selectedWeekIndex) { uiState.weeks.size }

    LaunchedTracker(viewModel, uiState, dayPagerState, weekPagerState)

    SwipeRefreshBox(
        isRefreshing = false,
        onSwipeRefresh = { viewModel.setRefreshPopupVisibility(true) },
        modifier = modifier
    ) {
        Column {
            MediumSpacer()
            MonthInfoRow(
                viewModel,
                uiState,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            MediumSpacer()
            WeekInfoRow(
                uiState,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            DatePicker(
                viewModel,
                uiState,
                weekPagerState,
                modifier = Modifier.fillMaxWidth()
            )
            SmallSpacer()
            SchedulePager(
                uiState.days,
                !uiState.isDayAutoScrollInProgress,
                dayPagerState,
                { element ->
                    viewModel.setSwitchElement(element)
                    isSwitchOptionsAlertVisible = true
                },
                modifier = Modifier.fillMaxSize()
            )
            LargeSpacer()
        }

        AttentionAlert(
            isVisible = uiState.isRefreshAlertVisible,
            text = stringResource(Res.string.refresh_alert_text),
            actionButtonText = stringResource(Res.string.Refresh),
            onAction = { viewModel.loadSchedule(refresh = true) },
            onDismiss = { viewModel.setRefreshPopupVisibility(false) },
        )

        SwitchAlert(
            isVisible = isSwitchOptionsAlertVisible,
            scheduleScreenUiState = uiState,
            onSelectOption = { switchOptions -> viewModel.recalculateWindows(switchOptions) },
            onDismiss = { isSwitchOptionsAlertVisible = false },
        )
    }
}

@Composable
fun EmptySchedule(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Image(
                painterResource(Res.drawable.clouds),
                contentDescription = null,
                modifier = Modifier.size(150.dp)
            )
            LargeSpacer()
            Text(
                stringResource(Res.string.no_schedule_full),
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ScheduleScreen(
    viewModel: ScheduleScreenViewModel,
) {
    val scheduleState by viewModel.scheduleState.collectAsState()
    when (scheduleState) {
        is ScheduleState.Loading, ScheduleState.LoadingFromWeb -> {
            val string = if (scheduleState is ScheduleState.Loading) {
                stringResource(Res.string.loading_schedule)
            } else {
                stringResource(Res.string.loading_schedule_from_web)
            }
            LoadingScreen(Modifier.fillMaxSize(), text = string)
        }
        is ScheduleState.Error ->
            ErrorScreenWithReloadButton(
                exception = (scheduleState as ScheduleState.Error).exception,
                onClick = { viewModel.loadSchedule(refresh = true) },
                modifier = Modifier.fillMaxSize()
            )
        is ScheduleState.Success -> {
            ScheduleBox(viewModel)
        }
    }
}
