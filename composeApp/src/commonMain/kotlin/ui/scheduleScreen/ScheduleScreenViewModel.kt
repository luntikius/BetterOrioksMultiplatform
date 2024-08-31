package ui.scheduleScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.DatabaseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class ScheduleScreenViewModel(
    val databaseRepository: DatabaseRepository
) : ViewModel() {

    private lateinit var _uiState: MutableStateFlow<ScheduleScreenUiState>
    private val _isInitialized = MutableStateFlow(false)

    init {
        refreshSchedule()
    }

    val isInitialized = _isInitialized.asStateFlow()
    lateinit var uiState: StateFlow<ScheduleScreenUiState>

    fun selectDayByIndex(index: Int) {
        selectWeekByDayIndex(index)
        _uiState.update { uis -> uis.copy(selectedDayIndex = index) }
    }

    fun selectDayByDate(date: LocalDate) {
        val dayIndex = _uiState.value.days.indexOfFirst { it.date == date }
        selectDayByIndex(dayIndex)
    }

    fun selectToday() {
        val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        selectDayByDate(today.date)
    }

    private fun selectWeekByDayIndex(index: Int) {
        val day = uiState.value.days[index]
        val weekIndex = uiState.value.weeks.indexOfFirst { it.number == day.weekNumber }
        selectWeekByIndex(weekIndex)
    }

    fun selectWeekByIndex(index: Int) {
        _uiState.update { uis -> uis.copy(selectedWeekIndex = index) }
    }

    fun setDayAutoscroll(value: Boolean) {
        _uiState.update { uis -> uis.copy(isDayAutoScrollInProgress = value) }
    }

    fun setWeekAutoscroll(value: Boolean) {
        _uiState.update { uis -> uis.copy(isWeekAutoScrollInProgress = value) }
    }

    fun refreshSchedule() {
        viewModelScope.launch {
            _isInitialized.update { false }
            val schedule = databaseRepository.getSchedule()
            val uiStateValue = ScheduleScreenUiState(schedule = schedule)
            _uiState = MutableStateFlow(uiStateValue)
            uiState = _uiState.asStateFlow()
            _isInitialized.update { true }
        }
    }
}
