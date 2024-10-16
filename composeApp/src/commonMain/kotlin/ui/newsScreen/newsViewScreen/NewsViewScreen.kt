package ui.newsScreen.newsViewScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import betterorioks.composeapp.generated.resources.Res
import betterorioks.composeapp.generated.resources.Resources
import betterorioks.composeapp.generated.resources.files
import betterorioks.composeapp.generated.resources.loading_news
import betterorioks.composeapp.generated.resources.news_view
import model.news.newsViewScreen.NewsViewContent
import model.news.newsViewScreen.NewsViewState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import ui.common.DefaultHeader
import ui.common.ErrorScreenWithReloadButton
import ui.common.LargeSpacer
import ui.common.LoadingScreen
import ui.common.MediumSpacer
import ui.common.SmallSpacer
import ui.common.XLargeSpacer
import utils.BetterOrioksFormats

@Composable
fun FileItem(
    file: Pair<String, String>,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = modifier,
        onClick = { }
    ) {
        Text(file.first, modifier.padding(16.dp))
    }
}

@Composable
fun FileItems(
    files: List<Pair<String, String>>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row {
            Icon(
                painter = painterResource(Res.drawable.files),
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            SmallSpacer()
            Text(
                text = stringResource(Res.string.Resources),
                style = MaterialTheme.typography.headlineSmall,
            )
        }
        LargeSpacer()
        LazyRow {
            items(files) {
                FileItem(
                    file = it,
                    modifier = Modifier.fillParentMaxWidth(0.4f)
                )
            }
        }
    }
}


@Composable
fun NewsContent(
    newsViewContent: NewsViewContent,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 8.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                modifier = Modifier.fillParentMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        BetterOrioksFormats.NEWS_DATE_TIME_FORMAT.format(newsViewContent.date),
                        style = MaterialTheme.typography.labelMedium
                    )
                    MediumSpacer()
                    Text(
                        newsViewContent.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            LargeSpacer()
        }
        items(newsViewContent.content) {
            MediumSpacer()
            Text(it, modifier.padding(horizontal = 8.dp))
        }
        item {
            XLargeSpacer()
            if(newsViewContent.files.isNotEmpty()) FileItems(newsViewContent.files)
        }
    }
}

@Composable
fun NewsViewScreen(
    id: String,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val newsViewViewModel = koinViewModel<NewsViewViewModel>(parameters = { parametersOf(id) })
    val uiState by newsViewViewModel.uiState.collectAsState()

    Column(
        modifier = modifier.padding(8.dp)
    ) {
        DefaultHeader(
            stringResource(Res.string.news_view),
            onBackButtonClick = { navController.popBackStack() },
        )
        MediumSpacer()
        when (uiState) {
            is NewsViewState.NotStarted,
            is NewsViewState.Loading -> LoadingScreen(text = stringResource(Res.string.loading_news))

            is NewsViewState.Success -> NewsContent(
                (uiState as NewsViewState.Success).newsViewContent
            )

            is NewsViewState.Error -> ErrorScreenWithReloadButton(
                (uiState as NewsViewState.Error).message,
                newsViewViewModel::getNewsContent,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}