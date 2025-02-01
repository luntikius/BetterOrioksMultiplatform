package ui.newsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.OrioksWebRepository
import data.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import model.news.NewsState

class NewsViewModel(
    private val subjectId: String?,
    private val userPreferencesRepository: UserPreferencesRepository,
    private val orioksWebRepository: OrioksWebRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(NewsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        if (subjectId != null) {
            _uiState.update { uis -> uis.copy(newsType = OrioksWebRepository.NewsType.Subject) }
        }
    }

    fun getNews() {
        viewModelScope.launch {
            _uiState.update { it.copy(newsState = NewsState.Loading) }
            try {
                val authData = userPreferencesRepository.authData.first()
                val news = orioksWebRepository.getNews(authData, uiState.value.newsType, subjectId)
                _uiState.update { it.copy(newsState = NewsState.Success(news)) }
            } catch (e: Exception) {
                _uiState.update { it.copy(newsState = NewsState.Error(e)) }
            }
        }
    }
}
