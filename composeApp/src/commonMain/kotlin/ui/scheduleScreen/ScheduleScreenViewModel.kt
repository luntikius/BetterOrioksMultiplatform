package ui.scheduleScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.MietWebRepository
import data.OrioksWebRepository
import data.ScheduleDatabaseRepository
import data.UserPreferencesRepository
import handlers.ToastHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import model.schedule.Schedule
import model.schedule.ScheduleClass
import model.schedule.ScheduleState
import model.schedule.SemesterDates
import model.schedule.SwitchOptions
import model.schedule.ToastState
import model.user.UserInfo
import utils.toSemesterLocalDate

class ScheduleScreenViewModel(
    private val scheduleDatabaseRepository: ScheduleDatabaseRepository,
    private val mietWebRepository: MietWebRepository,
    private val orioksWebRepository: OrioksWebRepository,
    private val userPreferencesRepository: UserPreferencesRepository,
    private val toastHandler: ToastHandler
) : ViewModel() {
    private lateinit var _uiState: MutableStateFlow<ScheduleScreenUiState>
    private val _scheduleState: MutableStateFlow<ScheduleState> = MutableStateFlow(ScheduleState.Loading)

    init {
        loadSchedule()
    }

    val scheduleState = _scheduleState.asStateFlow()
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

    private fun updateSchedule(schedule: Schedule) {
        _uiState.update { uis ->
            uis.copy(
                schedule = schedule,
                weeks = schedule.weeks,
                days = schedule.weeks.flatMap { it.days }
            )
        }
    }

    private suspend fun getUserInfo(refresh: Boolean): UserInfo {
        if (userPreferencesRepository.userInfo.first().group.isBlank() || refresh) {
            val authData = userPreferencesRepository.authData.first()
            val userInfo = orioksWebRepository.getUserInfo(authData)
            userPreferencesRepository.setUserInfo(userInfo)
        }
        return userPreferencesRepository.userInfo.first()
    }

    private suspend fun getSemesterDates(refresh: Boolean): SemesterDates {
        if (userPreferencesRepository.semesterDates.first().startDate.isBlank() || refresh) {
            val authData = userPreferencesRepository.authData.first()
            val semesterDates = orioksWebRepository.getSemesterDates(authData)
            userPreferencesRepository.setSemesterDates(semesterDates)
        }
        return userPreferencesRepository.semesterDates.first()
    }

    private suspend fun loadScheduleFromWeb(group: String, semesterStartDate: LocalDate) {
        val schedule = mietWebRepository.getSchedule(group).toScheduleDbEntities(semesterStartDate)
        scheduleDatabaseRepository.insertNewSchedule(schedule)
    }

    fun loadSchedule(refresh: Boolean = false) {
        viewModelScope.launch {
            try {
                _scheduleState.update { ScheduleState.Loading }

                if (refresh || !scheduleDatabaseRepository.isScheduleStored()) {
                    _scheduleState.update { ScheduleState.LoadingFromWeb }
                    val userInfo = getUserInfo(refresh)
                    val semesterDates = getSemesterDates(refresh)
                    val startDate = semesterDates.startDate.toSemesterLocalDate()
                    loadScheduleFromWeb(userInfo.group, startDate)
                    _scheduleState.update { ScheduleState.Loading }
                }

                val schedule = scheduleDatabaseRepository.getSchedule()
                val uiStateValue = ScheduleScreenUiState(schedule = schedule)
                _uiState = MutableStateFlow(uiStateValue)
                uiState = _uiState.asStateFlow()

                _scheduleState.update {
                    ScheduleState.Success(
                        if (refresh) ToastState.SUCCESS_TOAST else ToastState.NO_TOAST
                    )
                }
                selectToday()
            } catch (e: Exception) {
                println(e.stackTraceToString())
                if (!scheduleDatabaseRepository.isScheduleStored()) {
                    _scheduleState.update { ScheduleState.Error(e) }
                } else {
                    _scheduleState.update { ScheduleState.Success(toastState = ToastState.FAIL_TOAST) }
                    selectToday()
                }
            }
        }
    }

    fun setSwitchElement(element: ScheduleClass) {
        _uiState.update { uis -> uis.copy(switchElement = element) }
    }

    fun recalculateWindows(
        switchOptions: SwitchOptions = SwitchOptions.SWITCH_EVERY_TWO_WEEKS
    ) {
        viewModelScope.launch {
            scheduleDatabaseRepository.recalculateWindows(uiState.value.switchElement, switchOptions)
            val schedule = scheduleDatabaseRepository.getSchedule()
            updateSchedule(schedule)
        }
    }
}
