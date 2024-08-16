package ui.scheduleScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import model.ScheduleDay

private const val REFRESH_DELAY = 5000L

class ScheduleScreenViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ScheduleScreenUiState())
    val uiState = _uiState.asStateFlow()

    fun selectDay(day: ScheduleDay) {
        _uiState.update { uis -> uis.copy(selectedDay = day) }
    }

    fun selectIndex(index: Int) {
        val day = _uiState.value.schedule[index]
        selectDay(day)
    }

    private fun selectDate(date: LocalDate) {
        val day = _uiState.value.schedule.find { it.date == date }
        if (day != null) selectDay(day)
    }

    fun selectToday() {
        val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        selectDate(today.date)
    }

    fun refreshSchedule() {
        viewModelScope.launch {
            _uiState.update { uis -> uis.copy(isRefreshing = true) }
            delay(REFRESH_DELAY)
            _uiState.update { uis -> uis.copy(isRefreshing = false) }
        }
    }
}
