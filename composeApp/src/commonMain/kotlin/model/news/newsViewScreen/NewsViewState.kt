package model.news.newsViewScreen

sealed interface NewsViewState {
    data object NotStarted : NewsViewState
    data object Loading : NewsViewState
    data class Success(val newsText: String) : NewsViewState
    data class Error(val message: String) : NewsViewState
}