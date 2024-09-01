package ui.scheduleScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.DatabaseRepository
import data.MietWebRepository
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
    val databaseRepository: DatabaseRepository,
    val mietWebRepository: MietWebRepository
) : ViewModel() {

    private lateinit var _uiState: MutableStateFlow<ScheduleScreenUiState>
    private val _isInitialized = MutableStateFlow(false)

    init {
        loadSchedule()
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
        val first = uiState.value.days.first()
        val last = uiState.value.days.last()
        if (today.date in first.date..last.date) {
            selectDayByDate(today.date)
        } else {
            if (today.date < first.date) {
                selectDayByIndex(0)
            } else {
                selectDayByIndex(uiState.value.days.size - 1)
            }
        }
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

    private fun loadScheduleFromWeb(group: String, semesterStartDate: LocalDate) {
        viewModelScope.launch {
            val schedule = mietWebRepository.getSchedule(group).toScheduleDbEntities(semesterStartDate)
            databaseRepository.insertNewSchedule(schedule)
        }
    }

    fun loadSchedule(refresh: Boolean = false) {
        viewModelScope.launch {
            _isInitialized.update { false }

            if (refresh || !databaseRepository.isScheduleStored()) loadScheduleFromWeb(
                "ПИН-45",
                LocalDate(2024,9,2)
            )

            val schedule = databaseRepository.getSchedule()
            val uiStateValue = ScheduleScreenUiState(schedule = schedule)
            _uiState = MutableStateFlow(uiStateValue)
            uiState = _uiState.asStateFlow()

            _isInitialized.update { true }
        }
    }
}
