package ui.newsScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import betterorioks.composeapp.generated.resources.Res
import betterorioks.composeapp.generated.resources.loading_news
import betterorioks.composeapp.generated.resources.news
import betterorioks.composeapp.generated.resources.news_date
import model.AppScreens
import model.news.News
import model.news.NewsState
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import ui.common.DefaultHeader
import ui.common.ErrorScreenWithReloadButton
import ui.common.LargeSpacer
import ui.common.LoadingScreen
import utils.BetterOrioksFormats.NEWS_DATE_TIME_FORMAT

@Composable
fun NewsItem(
    news: News,
    onNewsClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        onClick = { onNewsClick(news.id) },
        modifier = modifier.padding(8.dp)
    ) {
        Column {
            Text(
                text = news.date?.let { stringResource(Res.string.news_date, NEWS_DATE_TIME_FORMAT.format(it)) } ?: "",
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = news.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 3,
                minLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            LargeSpacer()
        }
    }
}

@Composable
fun NewsContent(
    news: List<News>,
    onNewsClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(news) {
            NewsItem(news = it, onNewsClick, modifier = Modifier.fillMaxWidth())
            HorizontalDivider()
        }
    }
}

@Composable
fun NewsScreen(
    subjectId: String?,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val newsViewModel = koinViewModel<NewsViewModel>(
        parameters = { parametersOf(subjectId) }
    )
    val uiState by newsViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        if (uiState.newsState is NewsState.Loading) {
            newsViewModel.getNews()
        }
    }

    Column(
        modifier = modifier.padding(8.dp)
    ) {
        DefaultHeader(
            stringResource(Res.string.news),
            onBackButtonClick = { navController.popBackStack() },
        )
        when (uiState.newsState) {
            is NewsState.Loading -> LoadingScreen(
                text = stringResource(Res.string.loading_news),
                modifier = Modifier.fillMaxSize()
            )
            is NewsState.Success -> NewsContent(
                news = (uiState.newsState as NewsState.Success).news,
                onNewsClick = { navController.navigate("${AppScreens.NewsView.name}/$it/${uiState.newsType.name}") },
                modifier = Modifier.fillMaxSize()
            )
            is NewsState.Error -> ErrorScreenWithReloadButton(
                (uiState.newsState as NewsState.Error).exception,
                newsViewModel::getNews,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
