package ui.menuScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.ScheduleDatabaseRepository
import data.UserPreferencesRepository
import kotlinx.coroutines.launch

class MenuScreenViewModel(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val scheduleDatabaseRepository: ScheduleDatabaseRepository
) : ViewModel() {

    fun logout() {
        viewModelScope.launch {
            userPreferencesRepository.logout()
            scheduleDatabaseRepository.dumpAll()
        }
    }
}
