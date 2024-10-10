package ui.menuScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.UserPreferencesRepository
import kotlinx.coroutines.launch

class MenuScreenViewModel(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    fun logout() {
        viewModelScope.launch {
            userPreferencesRepository.logout()
        }
    }

}