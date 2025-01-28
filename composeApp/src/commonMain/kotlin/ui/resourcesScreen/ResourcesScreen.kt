package ui.resourcesScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import betterorioks.composeapp.generated.resources.Res
import betterorioks.composeapp.generated.resources.back_button
import betterorioks.composeapp.generated.resources.close
import betterorioks.composeapp.generated.resources.loading_resources
import model.request.ResponseState
import model.resources.DisplayResource
import model.resources.DisplayResourceCategory
import model.resources.ResourcePopupVisibilityState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import ui.common.DefaultHeader
import ui.common.ErrorScreenWithReloadButton
import ui.common.LargeSpacer
import ui.common.LoadingScreen
import ui.common.MediumSpacer
import ui.common.SmallSpacer
import utils.UrlHandler

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
    resourcePopupVisibilityState: ResourcePopupVisibilityState,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (resourcePopupVisibilityState is ResourcePopupVisibilityState.Visible) {
        val resources = resourcePopupVisibilityState.resources
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
                            resourcePopupVisibilityState.name,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(
                            onClick = onDismiss
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.close),
                                contentDescription = stringResource(Res.string.back_button),
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                    MediumSpacer()
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
                        modifier = Modifier.padding(8.dp).fillMaxSize()
                    ) {
                        LazyColumn {
                            items(resources) { resource ->
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

@Composable
fun ResourcesScreen(
    subjectName: String,
    disciplineId: String,
    scienceId: String,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val resourcesViewViewModel =
        koinViewModel<ResourcesViewModel>(parameters = { parametersOf(disciplineId, scienceId) })
    val uiState by resourcesViewViewModel.resourcesUiState.collectAsState()

    ResourcesPopup(
        resourcePopupVisibilityState = uiState.resourcePopupVisibility,
        onDismiss = resourcesViewViewModel::hideResourcePopup,
        modifier = Modifier.fillMaxSize()
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
