package ui.newsScreen.newsViewScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.OrioksWebRepository
import data.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import model.news.newsViewScreen.NewsViewState

class NewsViewViewModel(
    private val id: String,
    private val newsType: OrioksWebRepository.NewsType,
    private val userPreferencesRepository: UserPreferencesRepository,
    private val orioksWebRepository: OrioksWebRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<NewsViewState> = MutableStateFlow(NewsViewState.NotStarted)
    val uiState = _uiState.asStateFlow()

    init {
        getNewsContent()
    }

    fun getNewsContent() {
        viewModelScope.launch {
            _uiState.update { NewsViewState.Loading }
            try {
                val authData = userPreferencesRepository.authData.first()
                val newsViewContent = orioksWebRepository.getNewsViewContent(authData, id, newsType)
                _uiState.update { NewsViewState.Success(newsViewContent) }
            } catch (e: Exception) {
                _uiState.update { NewsViewState.Error(e) }
            }
        }
    }
}
