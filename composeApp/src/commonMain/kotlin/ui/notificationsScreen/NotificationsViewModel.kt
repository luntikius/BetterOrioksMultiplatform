package ui.notificationsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import betterorioks.composeapp.generated.resources.Res
import betterorioks.composeapp.generated.resources.notifications_permission_required
import data.NotificationsDatabaseRepository
import data.UserPreferencesRepository
import handlers.BackgroundHandler
import handlers.PermissionRequestHandler
import handlers.ToastHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import model.background.BackgroundTaskType
import org.jetbrains.compose.resources.getString

class NotificationsViewModel(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val backgroundHandler: BackgroundHandler,
    private val toastHandler: ToastHandler,
    private val permissionRequestHandler: PermissionRequestHandler,
    private val notificationRepository: NotificationsDatabaseRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(NotificationsUiState())
    val uiState = _uiState.asStateFlow()

    val notificationsHistoryFlow = notificationRepository.getNotificationsFlow()

    init {
        handleSettingsChanges()
    }

    private fun checkPermissions(onSuccess: () -> Unit) {
        viewModelScope.launch {
            val notificationPermissionRequiredString = getString(Res.string.notifications_permission_required)
            permissionRequestHandler.requestNotificationPermission { result ->
                if (result) {
                    onSuccess()
                } else {
                    toastHandler.makeShortToast(notificationPermissionRequiredString)
                }
            }
        }
    }

    fun toggleSubjectNotifications() {
        checkPermissions {
            viewModelScope.launch {
                val enable = !uiState.value.notificationSettings.isSubjectNotificationEnabled
                userPreferencesRepository.enableSubjectNotification(enable)
                if (enable) {
                    backgroundHandler.scheduleTask(BackgroundTaskType.SubjectNotifications)
                } else {
                    backgroundHandler.removeTask(BackgroundTaskType.SubjectNotifications)
                }
            }
        }
    }

    fun toggleNewsNotifications() {
        checkPermissions {
            viewModelScope.launch {
                val enable = !uiState.value.notificationSettings.isNewsNotificationsEnabled
                userPreferencesRepository.enableNewsNotifications(enable)
                if (enable) {
                    backgroundHandler.scheduleTask(BackgroundTaskType.NewsNotifications)
                } else {
                    backgroundHandler.removeTask(BackgroundTaskType.NewsNotifications)
                }
            }
        }
    }

    private fun handleSettingsChanges() {
        viewModelScope.launch(Dispatchers.IO) {
            userPreferencesRepository.notificationSettings.collect { notificationSettings ->
                _uiState.update { uis -> uis.copy(notificationSettings = notificationSettings) }
            }
        }
    }

    fun dumpNotificationsHistory() {
        viewModelScope.launch { notificationRepository.dumpNotificationsHistory() }
    }

    fun setInfoPopupVisibility(visible: Boolean) {
        _uiState.update { uis -> uis.copy(isInfoPopupVisible = visible) }
    }

}