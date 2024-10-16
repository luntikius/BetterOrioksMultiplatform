package ui.newsScreen.newsViewScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import betterorioks.composeapp.generated.resources.Res
import betterorioks.composeapp.generated.resources.loading_news
import betterorioks.composeapp.generated.resources.news_view
import model.news.newsViewScreen.NewsViewState
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import ui.common.DefaultHeader
import ui.common.ErrorScreenWithReloadButton
import ui.common.LoadingScreen

@Composable
fun NewsText(
    text: String
) {
    Text(
        text
    )
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
        when (uiState) {
            is NewsViewState.NotStarted,
            is NewsViewState.Loading -> LoadingScreen(text = stringResource(Res.string.loading_news))

            is NewsViewState.Success -> NewsText(
                (uiState as NewsViewState.Success).newsText
            )

            is NewsViewState.Error   -> ErrorScreenWithReloadButton(
                (uiState as NewsViewState.Error).message,
                newsViewViewModel::getNewsContent,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}