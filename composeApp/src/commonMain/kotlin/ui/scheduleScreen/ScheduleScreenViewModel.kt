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
import model.ScheduleWeek
import model.Temp

private const val REFRESH_DELAY = 5000L

class ScheduleScreenViewModel : ViewModel() {

    private var _uiState: MutableStateFlow<ScheduleScreenUiState>
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
        selectWeekByDay(day)
        _uiState.update { uis -> uis.copy(selectedDay = day) }
    }

    fun selectDayByIndex(index: Int) {
        val day = _uiState.value.days[index]
        selectDay(day)
    }

    fun selectDayByDate(date: LocalDate) {
        val day = _uiState.value.days.find { it.date == date }
        if (day != null) selectDay(day)
    }

    fun selectToday() {
        val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        selectDayByDate(today.date)
    }

    private fun selectWeek(week: ScheduleWeek) {
        _uiState.update { uis -> uis.copy(selectedWeek = week) }
    }

    private fun selectWeekByDay(day: ScheduleDay) {
        val week = uiState.value.weeks.find { it.number == day.weekNumber }
        if (week != null) selectWeek(week)
    }

    fun selectWeekByIndex(index: Int) {
        val week = _uiState.value.weeks[index]
        selectWeek(week)
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
            _uiState.update { getUiState() }
            delay(REFRESH_DELAY)
            _isInitialized.update { true }
        }
    }
}
