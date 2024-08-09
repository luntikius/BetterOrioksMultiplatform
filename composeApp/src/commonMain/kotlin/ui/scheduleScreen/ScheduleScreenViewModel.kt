package ui.scheduleScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

private const val REFRESH_DELAY = 5000L

class ScheduleScreenViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ScheduleScreenUiState())
    val uiState = _uiState.asStateFlow()

    fun selectDate(date: LocalDate) {
        _uiState.update { uis -> uis.copy(selectedDate = date) }
    }

    fun selectIndex(index: Int) {
        val date = _uiState.value.schedule[index].date
        selectDate(date)
    }

    fun refreshSchedule() {
        viewModelScope.launch {
            _uiState.update { uis -> uis.copy(isRefreshing = true) }
            delay(REFRESH_DELAY)
            _uiState.update { uis -> uis.copy(isRefreshing = false) }
        }
    }
}
