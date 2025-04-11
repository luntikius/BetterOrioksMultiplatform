package ui.newsScreen.newsViewScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.selection.SelectionContainer
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import betterorioks.composeapp.generated.resources.Res
import betterorioks.composeapp.generated.resources.attached_files
import betterorioks.composeapp.generated.resources.files
import betterorioks.composeapp.generated.resources.loading_news
import betterorioks.composeapp.generated.resources.news_view
import betterorioks.composeapp.generated.resources.social_orioks
import betterorioks.composeapp.generated.resources.web
import data.OrioksWebRepository
import handlers.UrlHandler
import model.news.newsViewScreen.NewsViewContent
import model.news.newsViewScreen.NewsViewState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.getKoin
import org.koin.compose.koinInject

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
    urlHandler: UrlHandler,
    modifier: Modifier = Modifier
) {
    val url = file.second
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = modifier,
        onClick = { urlHandler.handleUrl(url) }
    ) {
        Text(
            text = file.first,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun FileItems(
    files: List<Pair<String, String>>,
    urlHandler: UrlHandler,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(Res.drawable.files),
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            SmallSpacer()
            Text(
                text = stringResource(Res.string.attached_files),
                style = MaterialTheme.typography.titleMedium,
            )
        }
        LargeSpacer()
        LazyRow {
            items(files) {
                FileItem(
                    file = it,
                    urlHandler = urlHandler,
                    modifier = Modifier.fillParentMaxWidth(0.4f)
                )
            }
        }
    }
}

@Composable
fun NewsBodyItem(
    text: AnnotatedString,
    urlHandler: UrlHandler,
    modifier: Modifier = Modifier
) {
    MediumSpacer()
    SelectionContainer {
        ClickableText(
            text = text,
            modifier = modifier.padding(horizontal = 8.dp),
            style = MaterialTheme.typography.bodyMedium
        ) { offset ->
            text.getStringAnnotations(tag = NewsViewContent.URL_TAG, start = offset, end = offset).firstOrNull()?.let {
                urlHandler.handleUrl(it.item)
            }
        }
    }
}

@Composable
fun NewsContent(
    newsViewContent: NewsViewContent,
    urlHandler: UrlHandler = koinInject(),
    modifier: Modifier = Modifier
) {
    val contents = newsViewContent.getContentWithAnnotatedStrings()
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
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
            LargeSpacer()
        }
        items(contents) {
            NewsBodyItem(
                text = it,
                urlHandler = urlHandler
            )
        }
        item {
            XLargeSpacer()
            if (newsViewContent.files.isNotEmpty()) {
                FileItems(
                    files = newsViewContent.files,
                    urlHandler = urlHandler
                )
            }
        }
    }
}

@Composable
fun NewsViewScreen(
    id: String,
    newsType: OrioksWebRepository.NewsType,
    navController: NavController,
    modifier: Modifier = Modifier,
    urlHandler: UrlHandler = koinInject()
) {
    val koin = getKoin()
    val newsViewViewModel = viewModel {
        koin.get<NewsViewViewModel>(
            parameters = {
                parametersOf(id, newsType)
            }
        )
    }
    val uiState by newsViewViewModel.uiState.collectAsState()

    Column(
        modifier = modifier.padding(8.dp)
    ) {
        DefaultHeader(
            stringResource(Res.string.news_view),
            onBackButtonClick = { navController.popBackStack() },
            buttons = {
                IconButton(onClick = { urlHandler.handleUrl(getNewsUrl(id, newsType)) }) {
                    Icon(
                        painter = painterResource(Res.drawable.web),
                        contentDescription = stringResource(Res.string.social_orioks),
                        modifier = Modifier.size(32.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        )
        MediumSpacer()
        when (uiState) {
            is NewsViewState.NotStarted,
            is NewsViewState.Loading -> LoadingScreen(
                text = stringResource(Res.string.loading_news),
                modifier = Modifier.fillMaxSize()
            )

            is NewsViewState.Success -> NewsContent(
                (uiState as NewsViewState.Success).newsViewContent
            )

            is NewsViewState.Error -> ErrorScreenWithReloadButton(
                (uiState as NewsViewState.Error).exception,
                newsViewViewModel::getNewsContent,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

fun getNewsUrl(id: String, type: OrioksWebRepository.NewsType): String {
    return "https://orioks.miet.ru/${type.viewUrl}?id=$id"
}
