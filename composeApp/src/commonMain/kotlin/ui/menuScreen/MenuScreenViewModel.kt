package ui.menuScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.NotificationsDatabaseRepository
import data.OrioksWebRepository
import data.ScheduleDatabaseRepository
import data.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import model.user.UserInfoState

class MenuScreenViewModel(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val scheduleDatabaseRepository: ScheduleDatabaseRepository,
    private val orioksWebRepository: OrioksWebRepository,
    private val notificationsDatabaseRepository: NotificationsDatabaseRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MenuScreenUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            userPreferencesRepository.settings.collect { settings ->
                _uiState.update { uis -> uis.copy(iosNotificationsEnabled = settings.enableIosNotifications) }
            }
        }
    }

    private suspend fun updateUserInfo() {
        _uiState.update { uis -> uis.copy(userInfoState = UserInfoState.Loading) }
        try {
            val authData = userPreferencesRepository.authData.first()
            val userInfo = orioksWebRepository.getUserInfo(authData)
            userPreferencesRepository.setUserInfo(userInfo)
            _uiState.update { uis -> uis.copy(userInfoState = UserInfoState.Success(userInfo)) }
        } catch (e: Exception) {
            _uiState.update { uis -> uis.copy(userInfoState = UserInfoState.Error(e)) }
        }
    }

    fun getScreenData(refresh: Boolean = false) {
        if (refresh || uiState.value.userInfoState !is UserInfoState.Success) {
            viewModelScope.launch {
                updateUserInfo()
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            userPreferencesRepository.logout()
            scheduleDatabaseRepository.dumpAll()
            notificationsDatabaseRepository.dumpAll()
        }
    }
}
