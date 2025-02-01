package ui.newsScreen

import data.OrioksWebRepository
import model.news.NewsState

data class NewsUiState(
    val newsState: NewsState = NewsState.Loading,
    val newsType: OrioksWebRepository.NewsType = OrioksWebRepository.NewsType.Main
)
