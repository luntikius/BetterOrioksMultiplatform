package ui.resourcesScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.OrioksWebRepository
import data.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import model.request.ResponseState
import model.resources.DisplayResourceCategory
import model.resources.ResourcePopupVisibilityState

class ResourcesViewModel(
    private val disciplineId: String,
    private val scienceId: String,
    private val userPreferencesRepository: UserPreferencesRepository,
    private val orioksWebRepository: OrioksWebRepository
) : ViewModel() {

    private val _resourcesUiState = MutableStateFlow(ResourcesUiState())
    val resourcesUiState = _resourcesUiState.asStateFlow()

    init {
        getResources()
    }

    fun getResources(reload: Boolean = false) = _resourcesUiState.run {
        viewModelScope.launch {
            if (
                value.resourcesState is ResponseState.NotStarted ||
                value.resourcesState is ResponseState.Error ||
                reload
            ) {
                update { uis -> uis.copy(resourcesState = ResponseState.Loading()) }
                try {
                    val authData = userPreferencesRepository.authData.first()
                    val resourceCategories = orioksWebRepository.getResources(authData, disciplineId, scienceId)
                    update { uis ->
                        uis.copy(
                            resourcesState = ResponseState.Success(resourceCategories)
                        )
                    }
                } catch (e: Exception) {
                    update { uis -> uis.copy(resourcesState = ResponseState.Error(e)) }
                }
            }
        }
    }

    fun showResourcePopup(resourceCategory: DisplayResourceCategory) {
        _resourcesUiState.update { uis ->
            uis.copy(
                resourcePopupVisibility = ResourcePopupVisibilityState.Visible(
                    resourceCategory.name,
                    resourceCategory.resources
                )
            )
        }
    }

    fun hideResourcePopup() {
        _resourcesUiState.update { uis ->
            uis.copy(resourcePopupVisibility = ResourcePopupVisibilityState.Invisible)
        }
    }
}
