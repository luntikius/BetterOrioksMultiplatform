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
import model.Temp

private const val REFRESH_DELAY = 5000L

class ScheduleScreenViewModel : ViewModel() {

    private lateinit var _uiState: MutableStateFlow<ScheduleScreenUiState>
    private val _isInitialized = MutableStateFlow(false)

    init {
        _uiState = MutableStateFlow(getUiState())
        _isInitialized.update { true }
    }

    val isInitialized = _isInitialized.asStateFlow()
    val uiState = _uiState.asStateFlow()

    private fun getUiState(): ScheduleScreenUiState {
        return ScheduleScreenUiState(
            schedule = Temp.schedule
        )
    }

    fun selectDay(day: ScheduleDay) {
        _uiState.update { uis -> uis.copy(selectedDay = day, selectedMonth = day.date) }
    }

    fun selectIndex(index: Int) {
        val day = _uiState.value.days[index]
        selectDay(day)
    }

    fun selectDate(date: LocalDate) {
        val day = _uiState.value.days.find { it.date == date }
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
