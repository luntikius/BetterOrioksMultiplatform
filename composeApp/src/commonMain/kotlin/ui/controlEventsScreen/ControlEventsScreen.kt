package ui.controlEventsScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import betterorioks.composeapp.generated.resources.Res
import betterorioks.composeapp.generated.resources.Resources
import betterorioks.composeapp.generated.resources.arrow_left
import betterorioks.composeapp.generated.resources.attachment
import betterorioks.composeapp.generated.resources.back_button
import betterorioks.composeapp.generated.resources.buffer_text_exam
import betterorioks.composeapp.generated.resources.content_description_subject_info
import betterorioks.composeapp.generated.resources.control_form
import betterorioks.composeapp.generated.resources.exam_info_consultation
import betterorioks.composeapp.generated.resources.exam_info_exam
import betterorioks.composeapp.generated.resources.info
import betterorioks.composeapp.generated.resources.loading_subjects
import betterorioks.composeapp.generated.resources.moodle
import betterorioks.composeapp.generated.resources.moodle_course
import betterorioks.composeapp.generated.resources.news
import betterorioks.composeapp.generated.resources.no_teachers
import betterorioks.composeapp.generated.resources.profile
import betterorioks.composeapp.generated.resources.resources
import betterorioks.composeapp.generated.resources.room_number
import betterorioks.composeapp.generated.resources.teacher
import betterorioks.composeapp.generated.resources.teachers
import handlers.BufferHandler
import handlers.UrlHandler
import model.BetterOrioksScreen
import model.request.ResponseState
import model.subjectPerformance.ControlEventsListItem
import model.subjectPerformance.DisplaySubjectPerformance
import model.subjects.DisplayTeacher
import model.subjects.ExamInfo
import model.subjects.SubjectListItem
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import ui.common.BetterOrioksPopup
import ui.common.ErrorScreenWithReloadButton
import ui.common.LargeSpacer
import ui.common.LoadingScreen
import ui.common.MediumSpacer
import ui.common.SimpleIconButton
import ui.common.SmallSpacer
import ui.common.SwipeRefreshBox
import ui.common.XLargeSpacer
import ui.resourcesScreen.ResourcesPopup
import ui.subjectsScreen.PointsDisplay
import utils.disabled

@Composable
fun ExamInfoItem(
    title: StringResource,
    subjectName: String,
    examInfo: ExamInfo,
    modifier: Modifier = Modifier,
    bufferHandler: BufferHandler = koinInject(),
) {
    val titleString = stringResource(title)
    val bufferTextExam = stringResource(
        Res.string.buffer_text_exam, titleString, subjectName, examInfo.dateString, examInfo.room
    )
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        onClick = { bufferHandler.copyToClipboard(bufferTextExam) },
        modifier = modifier
    ) {
        val examRoomString = stringResource(Res.string.room_number, examInfo.room)
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row {
                Text(
                    text = titleString,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = examInfo.dateString,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            SmallSpacer()
            Text(
                text = examRoomString,
                style = MaterialTheme.typography.labelSmall,
            )
        }
    }
}

@Composable
fun TeacherItem(
    teacher: DisplayTeacher,
    modifier: Modifier = Modifier,
    bufferHandler: BufferHandler = koinInject(),
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(Res.drawable.profile),
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            LargeSpacer()
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = teacher.name,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 2.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { bufferHandler.copyToClipboard(teacher.name) }
                )
                Text(
                    text = teacher.email,
                    style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 2.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { bufferHandler.copyToClipboard(teacher.email) }
                )
            }
        }
    }
}

@Composable
fun InfoPopup(
    uiState: ControlEventsUiState,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (uiState.infoPopupVisibility is InfoPopupVisibilityState.Visible) {
        val subjectName = uiState.infoPopupVisibility.subjectListItem.name
        val info = uiState.infoPopupVisibility.subjectListItem.subjectInfo

        BetterOrioksPopup(
            title = subjectName,
            onDismiss = onDismiss,
            modifier = modifier
        ) {
            item {
                LargeSpacer()
                Text(
                    text = stringResource(Res.string.control_form, info.formOfControl.name),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            info.examInfo?.let { examInfo ->
                item {
                    LargeSpacer()
                    ExamInfoItem(
                        title = Res.string.exam_info_exam,
                        subjectName = subjectName,
                        examInfo = examInfo,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
            info.consultationInfo?.let { examInfo ->
                item {
                    LargeSpacer()
                    ExamInfoItem(
                        title = Res.string.exam_info_consultation,
                        subjectName = subjectName,
                        examInfo = examInfo,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
            item {
                val teacherRes = when (info.teachers.size) {
                    0 -> Res.string.no_teachers
                    1 -> Res.string.teacher
                    else -> Res.string.teachers
                }
                XLargeSpacer()
                Text(
                    text = stringResource(teacherRes),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            items(info.teachers) { teacher ->
                LargeSpacer()
                TeacherItem(
                    teacher = teacher,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            item {
                LargeSpacer()
            }
        }
    }
}

@Composable
fun ControlEventsHeaderButtons(
    uiState: ControlEventsUiState,
    onBackButtonClick: () -> Unit,
    onInfoButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    SubcomposeLayout { constraints ->
        val placeable = subcompose(0) {
            Text(
                text = (uiState.displaySubjectPerformanceState as ResponseState.Success).result.subject.name,
                modifier = Modifier
                    .padding(8.dp),
                minLines = 2,
                maxLines = 2,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                overflow = TextOverflow.Ellipsis,
            )
        }.first().measure(constraints)
        val height = placeable.measuredHeight.toDp()

        val mainPlaceable = subcompose(1) {
            val isTextVisible = uiState.shouldShowNameInHeader
            Row(
                modifier = modifier.height(height),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackButtonClick, modifier = Modifier.size(40.dp)) {
                    Icon(
                        painter = painterResource(Res.drawable.arrow_left),
                        contentDescription = stringResource(Res.string.back_button),
                        modifier = Modifier.size(32.dp)
                    )
                }
                Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.Center) {
                    AnimatedVisibility(
                        visible = isTextVisible,
                        enter = fadeIn() + slideInVertically { it / 2 },
                        exit = fadeOut() + slideOutVertically { it / 2 }
                    ) {
                        Text(
                            text = (uiState.displaySubjectPerformanceState as ResponseState.Success)
                                .result.subject.name,
                            modifier = Modifier
                                .padding(8.dp),
                            maxLines = 2,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
                IconButton(onClick = onInfoButtonClick, modifier = Modifier.size(40.dp)) {
                    Icon(
                        painter = painterResource(Res.drawable.info),
                        contentDescription = stringResource(Res.string.content_description_subject_info),
                        modifier = Modifier.size(28.dp)
                    )
                }
                MediumSpacer()
            }
        }.first().measure(constraints)

        layout(constraints.maxWidth, mainPlaceable.measuredHeight) {
            mainPlaceable.place(0, 0)
        }
    }
}

@Composable
fun ControlEventsHeader(
    subjectListItem: SubjectListItem,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = modifier.wrapContentSize()
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                LargeSpacer()
                Text(
                    text = subjectListItem.name,
                    maxLines = 3,
                    modifier = Modifier.weight(1f)
                )
                LargeSpacer()
                PointsDisplay(subjectListItem)
                LargeSpacer()
            }
        }
    }
}

@Composable
fun NavigationItemsRow(
    onNewsButtonClick: () -> Unit,
    onResourcesButtonClick: () -> Unit,
    onMoodleButtonClick: (() -> Unit)?,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        SimpleIconButton(
            icon = painterResource(Res.drawable.news),
            text = stringResource(Res.string.news),
            onClick = onNewsButtonClick,
            modifier = Modifier.weight(1f),
            iconSize = 32
        )
        SimpleIconButton(
            icon = painterResource(Res.drawable.resources),
            text = stringResource(Res.string.Resources),
            onClick = onResourcesButtonClick,
            modifier = Modifier.weight(1f),
            iconSize = 32
        )
        if (onMoodleButtonClick != null) {
            SimpleIconButton(
                icon = painterResource(Res.drawable.moodle),
                text = stringResource(Res.string.moodle_course),
                onClick = onMoodleButtonClick,
                modifier = Modifier.weight(1f),
                iconSize = 32
            )
        }
    }
}

@Composable
fun ControlEventItem(
    viewModel: ControlEventsViewModel,
    controlEventItem: ControlEventsListItem.ControlEventItem,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable(
            enabled = controlEventItem.resources.isNotEmpty(),
            onClick = { viewModel.showResourcePopup(controlEventItem = controlEventItem) }
        )
    ) {
        XLargeSpacer()
        Column(
            modifier = Modifier.weight(1f)
        ) {
            LargeSpacer()
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = controlEventItem.fullName,
                    maxLines = 3,
                    style = MaterialTheme.typography.bodyLarge
                )
                if (controlEventItem.resources.isNotEmpty()) {
                    SmallSpacer()
                    Icon(
                        painterResource(Res.drawable.attachment),
                        "",
                        modifier = Modifier.size(16.dp).rotate(-45F),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
            if (controlEventItem.description != null) {
                SmallSpacer()
                Text(
                    text = controlEventItem.description,
                    maxLines = 3,
                    style = MaterialTheme.typography.labelSmall
                )
            }
            LargeSpacer()
        }
        LargeSpacer()
        Text(
            controlEventItem.getPointsAnnotatedString(),
        )
        XLargeSpacer()
    }
    LargeSpacer()
}

@Composable
fun WeeksLeftItem(
    weeksLeftItem: ControlEventsListItem.WeeksLeftItem,
    modifier: Modifier = Modifier
) {
    Card(
        shape = CircleShape,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.disabled()
        ),
        modifier = modifier.padding(start = 8.dp, top = 16.dp, bottom = 4.dp, end = 8.dp),
    ) {
        Text(
            weeksLeftItem.getWeeksLeftString(),
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun ControlEventsList(
    subjectPerformance: DisplaySubjectPerformance,
    viewModel: ControlEventsViewModel,
    navController: NavController,
    urlHandler: UrlHandler = koinInject(),
    modifier: Modifier = Modifier,
) {
    val state = rememberLazyListState()

    LaunchedEffect(state.firstVisibleItemIndex) {
        viewModel.onRecyclerFirstVisibleItemIndexChanged(state.firstVisibleItemIndex)
    }

    SwipeRefreshBox(
        onSwipeRefresh = viewModel::reloadSubjects,
        isRefreshing = false,
        modifier = modifier
    ) {
        LazyColumn(
            state = state,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                LargeSpacer()
                ControlEventsHeader(
                    subjectListItem = subjectPerformance.subject,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            item {
                subjectPerformance.subject.run {
                    LargeSpacer()
                    NavigationItemsRow(
                        onNewsButtonClick = {
                            navController.navigate(BetterOrioksScreen.NewsScreen(id)) { launchSingleTop = true }
                        },
                        onResourcesButtonClick = {
                            navController.navigate(BetterOrioksScreen.ResourcesScreen(id, scienceId, name)) { launchSingleTop = true }
                        },
                        onMoodleButtonClick = subjectPerformance.subject.moodleCourseUrl
                            ?.let { { urlHandler.handleUrl(it) } },
                        modifier = Modifier.padding(horizontal = 32.dp)
                    )
                }
            }
            items(subjectPerformance.controlEvents) { item ->
                when (item) {
                    is ControlEventsListItem.ControlEventItem -> {
                        ControlEventItem(viewModel, item)
                    }
                    is ControlEventsListItem.WeeksLeftItem -> {
                        WeeksLeftItem(item)
                    }
                }
            }
        }
    }
}

@Composable
fun ControlEventsContent(
    subjectPerformance: DisplaySubjectPerformance,
    navController: NavController,
    viewModel: ControlEventsViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.controlEventsUiState.collectAsState()

    ResourcesPopup(
        resourcePopupVisibilityState = uiState.resourcePopupVisibility,
        onDismiss = viewModel::hideResourcePopup,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.7f)
            .padding(horizontal = 16.dp)
    )

    InfoPopup(
        uiState = uiState,
        onDismiss = viewModel::hideInfoPopup,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.7f)
            .padding(horizontal = 16.dp)
    )

    Column(
        modifier = modifier
    ) {
        SmallSpacer()
        ControlEventsHeaderButtons(
            uiState = uiState,
            onBackButtonClick = { navController.navigateUp() },
            onInfoButtonClick = { viewModel.showInfoPopup(subjectPerformance.subject) },
            modifier = Modifier.padding(horizontal = 16.dp),
        )
        ControlEventsList(
            subjectPerformance = subjectPerformance,
            viewModel = viewModel,
            navController = navController
        )
    }
}

@Composable
fun ControlEventsScreen(id: String, navController: NavController) {
    val controlEventsViewViewModel = koinViewModel<ControlEventsViewModel>(parameters = { parametersOf(id) })
    val controlEventsUiState by controlEventsViewViewModel.controlEventsUiState.collectAsState()

    when (val displaySubjectPerformanceState = controlEventsUiState.displaySubjectPerformanceState) {
        is ResponseState.Success -> {
            ControlEventsContent(
                subjectPerformance = displaySubjectPerformanceState.result,
                navController = navController,
                viewModel = controlEventsViewViewModel
            )
        }
        is ResponseState.Error -> {
            ErrorScreenWithReloadButton(
                exception = displaySubjectPerformanceState.exception,
                onClick = { controlEventsViewViewModel.reloadSubjects() },
                modifier = Modifier.fillMaxSize()
            )
        }
        else -> {
            LoadingScreen(Modifier.fillMaxSize(), text = stringResource(Res.string.loading_subjects))
        }
    }
}
