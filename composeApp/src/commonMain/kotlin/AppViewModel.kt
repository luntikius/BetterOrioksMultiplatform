import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import model.settings.SettingsState

class AppViewModel(
    preferencesRepository: UserPreferencesRepository
) : ViewModel() {

    data class State(
        val isAuthorized: Boolean,
        val settings: SettingsState
    )

    private val _state: MutableStateFlow<State> = MutableStateFlow(
        State(
            isAuthorized = runBlocking { preferencesRepository.isAuthorized.first() },
            settings = runBlocking { preferencesRepository.settings.first() }
        )
    )

    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            preferencesRepository.isAuthorized.collect {
                _state.update { s -> s.copy(isAuthorized = it) }
            }
        }
        viewModelScope.launch {
            preferencesRepository.settings.collect {
                _state.update { s -> s.copy(settings = it) }
            }
        }
    }

}