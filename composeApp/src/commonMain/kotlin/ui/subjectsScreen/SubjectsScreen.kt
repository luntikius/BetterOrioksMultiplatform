package ui.subjectsScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import betterorioks.composeapp.generated.resources.Debts
import betterorioks.composeapp.generated.resources.Res
import betterorioks.composeapp.generated.resources.Semester
import betterorioks.composeapp.generated.resources.academic_performance_caps
import betterorioks.composeapp.generated.resources.cancel
import betterorioks.composeapp.generated.resources.change_semester
import betterorioks.composeapp.generated.resources.content_description_group_subjects
import betterorioks.composeapp.generated.resources.content_description_select_semester
import betterorioks.composeapp.generated.resources.loading_subjects
import betterorioks.composeapp.generated.resources.no_subjects
import betterorioks.composeapp.generated.resources.sort
import model.BetterOrioksScreen
import model.schedule.scheduleJson.Semester
import model.subjects.SubjectListItem
import model.subjects.SubjectsGroup
import model.subjects.SubjectsState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.common.BetterOrioksPopup
import ui.common.EmptyItem
import ui.common.ErrorScreenWithReloadButton
import ui.common.LargeSpacer
import ui.common.LoadingScreen
import ui.common.MediumSpacer
import ui.common.SmallSpacer
import ui.common.SwipeRefreshBox
import utils.disabled

@Composable
fun ChangeSemesterPopup(
    isVisible: Boolean,
    onSelected: (semesterId: String?) -> Unit,
    onDismiss: () -> Unit,
    semesters: List<Semester>,
    currentlySelectedSemesterId: String?,
    modifier: Modifier = Modifier
) {
    val currentSemesterId = semesters.lastOrNull { it.startDate != null }?.id
    val selectedSemesterId = currentlySelectedSemesterId ?: currentSemesterId

    if (isVisible) {
        BetterOrioksPopup(
            title = stringResource(Res.string.Semester),
            onDismiss = onDismiss,
            modifier = modifier.fillMaxWidth(0.85f),
            columnModifier = Modifier.wrapContentHeight(),
            buttons = {
                TextButton(
                    onClick = { onDismiss() },
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = stringResource(Res.string.cancel),
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            }
        ) {
            item { LargeSpacer() }
            items(semesters) { semester ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        onSelected(if (semester.id != currentSemesterId) semester.id else null)
                        onDismiss()
                    }.fillParentMaxWidth()
                ) {
                    RadioButton(
                        selected = selectedSemesterId == semester.id,
                        onClick = {
                            onSelected(semester.id)
                            onDismiss()
                        }
                    )
                    SmallSpacer()
                    Text(
                        text = semester.name,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            item { LargeSpacer() }
        }
    }
}

@Composable
fun SubjectsHeader(
    viewModel: SubjectsViewModel,
    modifier: Modifier = Modifier
) {
    var popupIsVisible by remember { mutableStateOf(false) }
    val subjectsState by viewModel.subjectsState.collectAsState()
    val uiState by viewModel.subjectsScreenUiState.collectAsState()
    val semesters = (subjectsState as? SubjectsState.Success)?.semesters ?: emptyList()
    val enabled = subjectsState is SubjectsState.Success

    ChangeSemesterPopup(
        isVisible = popupIsVisible,
        semesters = semesters,
        onSelected = viewModel::selectSemester,
        onDismiss = { popupIsVisible = false },
        currentlySelectedSemesterId = uiState.selectedSemesterId
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = stringResource(Res.string.academic_performance_caps),
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.weight(1f))

        IconButton(
            onClick = { popupIsVisible = true },
            modifier = Modifier.size(32.dp),
            enabled = enabled,
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = MaterialTheme.colorScheme.primary,
                disabledContentColor = MaterialTheme.colorScheme.primary.disabled()
            )
        ) {
            Icon(
                painter = painterResource(Res.drawable.change_semester),
                contentDescription = stringResource(Res.string.content_description_select_semester),
                modifier = Modifier.size(32.dp)
            )
        }

        LargeSpacer()

        IconButton(
            onClick = { viewModel.toggleGrouping() },
            modifier = Modifier.size(32.dp),
            enabled = enabled,
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = MaterialTheme.colorScheme.primary,
                disabledContentColor = MaterialTheme.colorScheme.primary.disabled()
            )
        ) {
            Icon(
                painter = painterResource(Res.drawable.sort),
                contentDescription = stringResource(Res.string.content_description_group_subjects),
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Composable
fun PointsDisplay(
    subject: SubjectListItem,
    modifier: Modifier = Modifier
) {
    Card(
        shape = CircleShape,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        modifier = modifier,
        border = subject.getBorder()
    ) {
        Row(
            modifier = modifier.padding(vertical = 8.dp, horizontal = 12.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                subject.getPointsAnnotatedString(),
            )
        }
    }
}

@Composable
fun SubjectItem(
    subject: SubjectListItem,
    onNavigateToSubject: (String) -> Unit,
    modifier: Modifier = Modifier,
    pointsDisplayModifier: Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = modifier.clip(RoundedCornerShape(16.dp)).clickable { onNavigateToSubject(subject.id) }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = subject.name,
                maxLines = 3,
                modifier = Modifier.weight(1f)
            )
            LargeSpacer()
            PointsDisplay(subject, modifier = pointsDisplayModifier)
        }
    }
}

@Composable
fun SubjectsColumn(
    subjectsState: SubjectsState.Success,
    isGroupingEnabled: Boolean,
    onNavigateToSubject: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val allSubjects = subjectsState.subjectListItems + subjectsState.debtSubjectListItems

    if (allSubjects.isEmpty()) {
        EmptyItem(
            title = stringResource(Res.string.no_subjects),
            modifier = modifier.fillMaxSize().scrollable(rememberScrollableState { 0f }, Orientation.Vertical)
        )
    } else {
        SubcomposeLayout { constraints ->
            val composables: List<@Composable () -> Unit> = allSubjects.map { @Composable { PointsDisplay(it) } }
            val placeables = composables.mapIndexed { index, composable ->
                val placeable = subcompose(index) {
                    composable()
                }.first().measure(constraints)
                placeable
            }

            val width = placeables.maxBy { it.measuredWidth }.measuredWidth.toDp()

            val mainPlaceable = subcompose(composables.size + 1) {
                LazyColumn(
                    modifier = modifier
                ) {
                    item {
                        if (isGroupingEnabled) {
                            GroupedSubjects(
                                subjects = subjectsState.subjectListItems,
                                onNavigateToSubject = onNavigateToSubject,
                                modifier = Modifier.fillParentMaxWidth(),
                                pointsDisplayModifier = Modifier.width(width)
                            )
                        } else {
                            UngroupedSubjects(
                                subjects = subjectsState.subjectListItems,
                                onNavigateToSubject = onNavigateToSubject,
                                modifier = Modifier.fillParentMaxWidth(),
                                pointsDisplayModifier = Modifier.width(width)
                            )
                        }
                    }
                    if (subjectsState.debtSubjectListItems.isNotEmpty()) {
                        item {
                            GroupOfSubjects(
                                groupName = stringResource(Res.string.Debts),
                                subjects = subjectsState.debtSubjectListItems,
                                onNavigateToSubject = onNavigateToSubject,
                                modifier = Modifier.fillParentMaxWidth(),
                                pointsDisplayModifier = Modifier.width(width)
                            )
                        }
                    }
                }
            }.first().measure(constraints)

            layout(constraints.maxWidth, constraints.maxHeight) {
                mainPlaceable.place(0, 0)
            }
        }
    }
}

@Composable
fun UngroupedSubjects(
    subjects: List<SubjectListItem>,
    onNavigateToSubject: (String) -> Unit,
    modifier: Modifier = Modifier,
    pointsDisplayModifier: Modifier
) {
    subjects.forEach {
        SubjectItem(
            subject = it,
            onNavigateToSubject = onNavigateToSubject,
            modifier = modifier,
            pointsDisplayModifier = pointsDisplayModifier
        )
        MediumSpacer()
    }
}

@Composable
fun GroupOfSubjects(
    groupName: String,
    subjects: List<SubjectListItem>,
    onNavigateToSubject: (String) -> Unit,
    modifier: Modifier = Modifier,
    pointsDisplayModifier: Modifier
) {
    MediumSpacer()
    Text(
        text = groupName,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black),
        modifier = modifier.padding(start = 16.dp)
    )
    MediumSpacer()
    subjects.forEach {
        SubjectItem(
            subject = it,
            onNavigateToSubject = onNavigateToSubject,
            modifier = modifier,
            pointsDisplayModifier = pointsDisplayModifier
        )
        MediumSpacer()
    }
}

@Composable
fun GroupedSubjects(
    subjects: List<SubjectListItem>,
    onNavigateToSubject: (String) -> Unit,
    modifier: Modifier = Modifier,
    pointsDisplayModifier: Modifier
) {
    SubjectsGroup.entries.forEach { group ->
        val filteredSubjects = subjects.filter { group.filterLambda(it) }
        if (filteredSubjects.isNotEmpty()) {
            GroupOfSubjects(
                groupName = stringResource(group.nameRes),
                subjects = filteredSubjects,
                onNavigateToSubject = onNavigateToSubject,
                modifier = modifier,
                pointsDisplayModifier = pointsDisplayModifier
            )
        }
    }
}

@Composable
fun SubjectsScreenContent(
    navController: NavController,
    subjectsState: SubjectsState,
    subjectsViewModel: SubjectsViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by subjectsViewModel.subjectsScreenUiState.collectAsState()
    val isGroupingEnabled by subjectsViewModel.isSubjectsGroupingEnabled.collectAsState(false)
    val onNavigateToSubject = { subjectId: String ->
        navController.navigate(
            BetterOrioksScreen.ControlEventsScreen(
                subjectId = subjectId,
                semesterId = uiState.selectedSemesterId
            )
        ) {
            launchSingleTop = true
        }
    }

    when (subjectsState) {
        is SubjectsState.Success -> {
            SwipeRefreshBox(
                onSwipeRefresh = { subjectsViewModel.getSubjects(true) },
                isRefreshing = false,
                modifier = modifier
            ) {
                SubjectsColumn(
                    subjectsState,
                    isGroupingEnabled = isGroupingEnabled,
                    onNavigateToSubject = onNavigateToSubject,
                    modifier = modifier,
                )
            }
        }
        is SubjectsState.Error -> {
            ErrorScreenWithReloadButton(
                exception = subjectsState.exception,
                onClick = { subjectsViewModel.getSubjects(reload = true) },
                modifier = modifier.fillMaxSize()
            )
        }
        else -> {
            LoadingScreen(modifier.fillMaxSize(), text = stringResource(Res.string.loading_subjects))
        }
    }
}

@Composable
fun SubjectsScreen(
    navController: NavController,
    subjectsViewModel: SubjectsViewModel,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        subjectsViewModel.getSubjects()
    }

    val subjectsState by subjectsViewModel.subjectsState.collectAsState()

    Column(
        modifier = modifier.fillMaxSize().padding(top = 16.dp, end = 16.dp, start = 16.dp)
    ) {
        SubjectsHeader(
            subjectsViewModel
        )
        LargeSpacer()
        SubjectsScreenContent(
            navController,
            subjectsState,
            subjectsViewModel
        )
    }
}
