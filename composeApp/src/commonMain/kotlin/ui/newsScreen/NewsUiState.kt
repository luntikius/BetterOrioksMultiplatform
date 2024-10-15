package ui.newsScreen

import model.news.NewsState

data class NewsUiState(
    val newsState: NewsState = NewsState.Loading
)
