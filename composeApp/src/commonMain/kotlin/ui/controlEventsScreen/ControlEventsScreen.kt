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
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import betterorioks.composeapp.generated.resources.Res
import betterorioks.composeapp.generated.resources.Resources
import betterorioks.composeapp.generated.resources.arrow_left
import betterorioks.composeapp.generated.resources.attachment
import betterorioks.composeapp.generated.resources.back_button
import betterorioks.composeapp.generated.resources.close
import betterorioks.composeapp.generated.resources.content_description_subject_info
import betterorioks.composeapp.generated.resources.info
import betterorioks.composeapp.generated.resources.loading_subjects
import betterorioks.composeapp.generated.resources.moodle
import betterorioks.composeapp.generated.resources.moodle_course
import betterorioks.composeapp.generated.resources.resources
import model.request.ResponseState
import model.subjectPerformance.ControlEventsListItem
import model.subjectPerformance.DisplayResource
import model.subjectPerformance.DisplaySubjectPerformance
import model.subjects.SubjectListItem
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import ui.common.ErrorScreenWithReloadButton
import ui.common.LargeSpacer
import ui.common.LoadingScreen
import ui.common.MediumSpacer
import ui.common.SimpleIconButton
import ui.common.SmallSpacer
import ui.common.SwipeRefreshBox
import ui.common.XLargeSpacer
import ui.subjectsScreen.PointsDisplay
import utils.UrlHandler
import utils.disabled

@Composable
fun ResourceItem(
    resource: DisplayResource,
    modifier: Modifier = Modifier,
    urlHandler: UrlHandler = koinInject()
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clickable(onClick = { urlHandler.handleUrl(resource.url) }).padding(start = 16.dp, end = 32.dp)
    ) {
        Icon(
            painter = painterResource(resource.iconRes),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(32.dp)
        )
        LargeSpacer()
        Column {
            LargeSpacer()
            Text(
                text = resource.name,
                maxLines = 3,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxWidth()
            )
            SmallSpacer()
            Text(
                text = resource.description,
                maxLines = 3,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.fillMaxWidth()
            )
            LargeSpacer()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResourcesPopup(
    uiState: ControlEventsUiState,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (uiState.resourcePopupVisibility is ResourcePopupVisibilityState.Visible) {
        val controlEventItem = uiState.resourcePopupVisibility.controlEventItem
        BasicAlertDialog(
            onDismissRequest = { onDismiss() },
            modifier = modifier,
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            )
        ) {
            Card(
                colors = CardDefaults.cardColors().copy(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                elevation = CardDefaults.cardElevation(0.dp),
            ) {
                Column(
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(start = 24.dp, end = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            controlEventItem.fullName,
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(
                            onClick = onDismiss
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.close),
                                contentDescription = stringResource(Res.string.back_button),
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                    LargeSpacer()
                    LazyColumn {
                        items(controlEventItem.resources) { resource ->
                            ResourceItem(
                                resource = resource,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
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
    onResourcesButtonClick: () -> Unit,
    onMoodleButtonClick: (() -> Unit)?,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
//        SimpleIconButton(
//            icon = painterResource(Res.drawable.notifications),
//            text = stringResource(Res.string.notifications),
//            onClick = onNotificationsButtonClick,
//            enabled = false,
//            modifier = Modifier.weight(1f),
//            iconSize = 32
//        )
        SimpleIconButton(
            icon = painterResource(Res.drawable.resources),
            text = stringResource(Res.string.Resources),
            onClick = onResourcesButtonClick,
            enabled = false,
            modifier = Modifier.weight(1f),
            iconSize = 32
        )
        if (onMoodleButtonClick != null) {
            SimpleIconButton(
                icon = painterResource(Res.drawable.moodle),
                text = stringResource(Res.string.moodle_course),
                onClick = onMoodleButtonClick,
                enabled = true,
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
                val scienceId = subjectPerformance.subject.scienceId

                LargeSpacer()
                NavigationItemsRow(
                    onResourcesButtonClick = {},
                    onMoodleButtonClick = subjectPerformance.subject.moodleCourseUrl?.let { { urlHandler.handleUrl(it) } } ,
                    modifier = Modifier.padding(horizontal = 32.dp)
                )
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
        uiState = uiState,
        onDismiss = viewModel::hideResourcePopup,
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
            onInfoButtonClick = { viewModel.showInfo() },
            modifier = Modifier.padding(horizontal = 16.dp),
        )
        ControlEventsList(
            subjectPerformance = subjectPerformance,
            viewModel = viewModel
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
