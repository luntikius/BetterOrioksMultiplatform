package ui.subjectsScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import betterorioks.composeapp.generated.resources.Res
import betterorioks.composeapp.generated.resources.academic_performance_caps
import betterorioks.composeapp.generated.resources.change_semester
import betterorioks.composeapp.generated.resources.content_description_group_subjects
import betterorioks.composeapp.generated.resources.content_description_select_semester
import betterorioks.composeapp.generated.resources.loading_subjects
import betterorioks.composeapp.generated.resources.sort
import model.subjects.DisplaySubject
import model.subjects.SubjectsState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.common.ErrorScreenWithReloadButton
import ui.common.LargeSpacer
import ui.common.LoadingScreen
import ui.common.MediumSpacer
import ui.common.SwipeRefreshBox
import utils.disabled

@Composable
fun SubjectsHeader(
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    var popupIsVisible by remember { mutableStateOf(false) }

    if (popupIsVisible) {
//        ChangeSemesterPopup(
//            semesters = semesters,
//            onChange = {i -> onSelectSemester(i)},
//            onDismiss = {popupIsVisible = false},
//            currentSelectedId = selectedSemesterId
//        )
    }

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
            onClick = { TODO() },
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
    subject: DisplaySubject,
    modifier: Modifier = Modifier
) {
    Card(
        shape = CircleShape,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                subject.getPointsAnnotatedString()
            )
        }
    }
}

@Composable
fun SubjectItem(
    subject: DisplaySubject,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = modifier
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
            PointsDisplay(subject)
        }
    }
}

@Composable
fun SubjectsColumn(
    subjects: List<DisplaySubject>,
    subjectsViewModel: SubjectsViewModel,
    modifier: Modifier = Modifier
) {
    SwipeRefreshBox(
        onSwipeRefresh = { subjectsViewModel.getSubjects(true) },
        isRefreshing = false,
        modifier = modifier
    ) {
        LazyColumn {
            items(subjects) {
                MediumSpacer()
                SubjectItem(it, modifier = Modifier.fillParentMaxWidth())
            }
        }
    }
}

@Composable
fun SubjectsScreenContent(
    subjectsState: SubjectsState,
    subjectsViewModel: SubjectsViewModel,
    modifier: Modifier = Modifier
) {
    when (subjectsState) {
        is SubjectsState.Success -> {
            SubjectsColumn(subjectsState.displaySubjects, subjectsViewModel = subjectsViewModel, modifier = modifier)
        }
        is SubjectsState.Error -> {
            ErrorScreenWithReloadButton(
                text = subjectsState.message,
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
        modifier = modifier.fillMaxSize().padding(16.dp)
    ) {
        SubjectsHeader(
            enabled = subjectsState is SubjectsState.Success
        )
        LargeSpacer()
        SubjectsScreenContent(
            subjectsState,
            subjectsViewModel
        )
    }
}
