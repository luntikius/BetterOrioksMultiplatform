package ui.settingsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import model.settings.Theme

class SettingsViewModel(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<SettingsUiState> = MutableStateFlow(SettingsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            userPreferencesRepository.settings.collect { settings ->
                _uiState.update { uis ->
                    uis.copy(
                        selectedTheme = settings.theme
                    )
                }
            }
        }
    }

    fun setTheme(theme: Theme) {
        viewModelScope.launch {
            userPreferencesRepository.setTheme(theme)
        }
    }
}