package model.news.newsViewScreen

sealed interface NewsViewState {
    data object NotStarted : NewsViewState
    data object Loading : NewsViewState
    data class Success(val newsViewContent: NewsViewContent) : NewsViewState
    data class Error(val exception: Exception) : NewsViewState
}