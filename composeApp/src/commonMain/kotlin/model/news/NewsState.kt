package model.news

sealed interface NewsState {
    data object Loading : NewsState
    data class Success(val news: List<News>) : NewsState
    data class Error(val exception: Exception) : NewsState
}
