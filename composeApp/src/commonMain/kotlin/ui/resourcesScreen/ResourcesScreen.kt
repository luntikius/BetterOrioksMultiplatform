package ui.resourcesScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import betterorioks.composeapp.generated.resources.Res
import betterorioks.composeapp.generated.resources.loading_resources
import handlers.UrlHandler
import model.request.ResponseState
import model.resources.DisplayResource
import model.resources.DisplayResourceCategory
import model.resources.ResourcePopupVisibilityState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.getKoin
import org.koin.compose.koinInject

import org.koin.core.parameter.parametersOf
import ui.common.BetterOrioksPopup
import ui.common.DefaultHeader
import ui.common.ErrorScreenWithReloadButton
import ui.common.LargeSpacer
import ui.common.LoadingScreen
import ui.common.SmallSpacer

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

@Composable
fun ResourcesPopup(
    resourcePopupVisibilityState: ResourcePopupVisibilityState,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (resourcePopupVisibilityState is ResourcePopupVisibilityState.Visible) {
        val resources = resourcePopupVisibilityState.resources
        BetterOrioksPopup(
            title = resourcePopupVisibilityState.name,
            onDismiss = onDismiss,
            modifier = modifier
        ) {
            items(resources) { resource ->
                ResourceItem(
                    resource = resource,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item { LargeSpacer() }
        }
    }
}

@Composable
fun ResourcesContent(
    viewModel: ResourcesViewModel,
    resourceCategories: List<DisplayResourceCategory>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 8.dp)
    ) {
        item {
            LargeSpacer()
        }
        items(resourceCategories) { category ->
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                onClick = { viewModel.showResourcePopup(category) },
                modifier = modifier.padding(8.dp)
            ) {
                Text(
                    text = category.name,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ResourcesScreen(
    subjectName: String,
    disciplineId: String,
    scienceId: String,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val koin = getKoin()
    val resourcesViewViewModel = viewModel {
        koin.get<ResourcesViewModel>(parameters = { parametersOf(disciplineId, scienceId) })
    }
    val uiState by resourcesViewViewModel.resourcesUiState.collectAsState()

    ResourcesPopup(
        resourcePopupVisibilityState = uiState.resourcePopupVisibility,
        onDismiss = resourcesViewViewModel::hideResourcePopup,
        modifier = Modifier.fillMaxWidth()
            .fillMaxHeight(0.85f)
            .padding(horizontal = 16.dp)
    )

    Column(
        modifier = modifier.padding(8.dp)
    ) {
        DefaultHeader(
            subjectName,
            onBackButtonClick = { navController.popBackStack() },
        )
        when (uiState.resourcesState) {
            is ResponseState.Loading, ResponseState.NotStarted -> LoadingScreen(
                text = stringResource(Res.string.loading_resources),
                modifier = Modifier.fillMaxSize()
            )
            is ResponseState.Success -> ResourcesContent(
                viewModel = resourcesViewViewModel,
                resourceCategories = (uiState.resourcesState as ResponseState.Success).result,
                modifier = Modifier.fillMaxSize()
            )
            is ResponseState.Error -> ErrorScreenWithReloadButton(
                exception = (uiState.resourcesState as ResponseState.Error).exception,
                onClick = { resourcesViewViewModel.getResources(reload = true) },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
