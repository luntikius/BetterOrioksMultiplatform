package ui.settingsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.UserPreferencesRepository
import handlers.NotificationsHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import model.settings.Theme

class SettingsViewModel(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val notificationsHandler: NotificationsHandler,
) : ViewModel() {
    private var counter = 0
    private val _uiState: MutableStateFlow<SettingsUiState> = MutableStateFlow(SettingsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            userPreferencesRepository.settings.collect { settings ->
                _uiState.update { uis ->
                    uis.copy(
                        selectedTheme = settings.theme,
                        softenDarkTheme = settings.softenDarkTheme,
                        pinkMode = settings.pinkMode
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

    fun setSoftenDarkTheme(enabled: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.setSoftenDarkTheme(enabled)
        }
    }

    fun setPinkMode(enabled: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.setPinkMode(enabled)
        }
    }

    fun onBuildNumberClick() {
        if (counter < 4) {
            counter++
        } else {
            _uiState.update { uis -> uis.copy(showFunSettings = true) }
        }
    }
}
