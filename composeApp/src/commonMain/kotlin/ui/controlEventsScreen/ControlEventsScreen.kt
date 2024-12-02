package ui.controlEventsScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import betterorioks.composeapp.generated.resources.Res
import betterorioks.composeapp.generated.resources.arrow_left
import betterorioks.composeapp.generated.resources.arrow_right
import betterorioks.composeapp.generated.resources.back_button
import model.request.ResponseState
import model.subjectPerformance.ControlEventsListItem
import model.subjectPerformance.DisplaySubjectPerformance
import model.subjectPerformance.SubjectPerformanceListItems
import model.subjects.SubjectListItem
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import ui.common.LargeSpacer
import ui.common.MediumSpacer
import ui.common.SmallSpacer
import ui.common.SwipeRefreshBox
import ui.subjectsScreen.PointsDisplay
import utils.disabled

@Composable
fun ControlEventsHeader(
    subjectListItem: SubjectListItem,
    onBackButtonClick: () -> Unit,
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
                IconButton(onClick = onBackButtonClick) {
                    Icon(
                        painter = painterResource(Res.drawable.arrow_left),
                        contentDescription = stringResource(Res.string.back_button),
                        modifier = Modifier.size(32.dp)
                    )
                }
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
fun ControlEventItem(
    controlEventItem: ControlEventsListItem.ControlEventItem,
    modifier: Modifier = Modifier
) {
    LargeSpacer()
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        LargeSpacer()
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = controlEventItem.fullName,
                maxLines = 3,
                style = MaterialTheme.typography.bodyLarge
            )
            if (controlEventItem.description != null) {
                SmallSpacer()
                Text(
                    text = controlEventItem.description,
                    maxLines = 3,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
        LargeSpacer()
        Text(
            controlEventItem.getPointsAnnotatedString(),
        )
        if (controlEventItem.resources.isNotEmpty()) {
            SmallSpacer()
            Icon(
                painter = painterResource(Res.drawable.arrow_right),
                contentDescription = stringResource(Res.string.back_button),
                modifier = Modifier.size(32.dp)
            )
            MediumSpacer()
        } else {
            LargeSpacer()
        }
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
    controlEventsListItems: SubjectPerformanceListItems,
    viewModel: ControlEventsViewModel,
    modifier: Modifier = Modifier
) {
    SwipeRefreshBox(
        onSwipeRefresh = viewModel::reloadSubjects,
        isRefreshing = false,
        modifier = modifier
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(controlEventsListItems.controlEvents) { item ->
                when (item) {
                    is ControlEventsListItem.ControlEventItem -> {
                        ControlEventItem(item)
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
    Column(
        modifier = modifier
    ) {
        LargeSpacer()
        ControlEventsHeader(
            subjectListItem = subjectPerformance.subject,
            onBackButtonClick = { navController.navigateUp() },
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        ControlEventsList(
            controlEventsListItems = subjectPerformance.subjectPerformanceListItems,
            viewModel = viewModel,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable
fun ControlEventsScreen(id: String, navController: NavController) {
    val controlEventsViewViewModel = koinViewModel<ControlEventsViewModel>(parameters = { parametersOf(id) })
    val controlEventsUiState by controlEventsViewViewModel.controlEventsUiState.collectAsState()

    if (controlEventsUiState.displaySubjectPerformanceState is ResponseState.Success) {
        val subjectPerformanceDisplay =
            (controlEventsUiState.displaySubjectPerformanceState as ResponseState.Success<DisplaySubjectPerformance>)
                .result
        ControlEventsContent(
            subjectPerformance = subjectPerformanceDisplay,
            navController = navController,
            viewModel = controlEventsViewViewModel
        )
    }
}
