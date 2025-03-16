package ui.loginScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.OrioksWebRepository
import data.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import model.login.LoginRequiredReason
import model.login.LoginState

class LoginScreenViewModel(
    private val orioksWebRepository: OrioksWebRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginScreenUiState())
    val uiState = _uiState.asStateFlow()

    init {
        checkInvalidation()
    }

    private fun checkInvalidation() {
        viewModelScope.launch {
            if (userPreferencesRepository.isSessionInvalidated()) {
                _uiState.update { uis ->
                    uis.copy(loginState = LoginState.LoginRequired(LoginRequiredReason.SESSION_EXPIRED))
                }
            } else {
                _uiState.update { uis ->
                    uis.copy(loginState = LoginState.IntroductionRequired)
                }
            }
        }
    }

    fun setLogin(login: String) {
        _uiState.update { uis -> uis.copy(login = login, isError = false) }
    }

    fun setPassword(password: String) {
        _uiState.update { uis -> uis.copy(password = password, isError = false) }
    }

    fun tryLogin() {
        viewModelScope.launch {
            try {
                _uiState.update { uis ->
                    uis.copy(loginState = LoginState.Loading)
                }
                val authData =
                    orioksWebRepository.getAuthData(uiState.value.login, uiState.value.password)
                userPreferencesRepository.setAuthData(authData)
                _uiState.update { uis ->
                    uis.copy(loginState = LoginState.Success)
                }
            } catch (e: NoSuchElementException) {
                _uiState.update { uis ->
                    uis.copy(
                        loginState = LoginState.LoginRequired(reason = LoginRequiredReason.BAD_LOGIN_OR_PASSWORD),
                        isError = true,
                        password = ""
                    )
                }
            } catch (e: Exception) {
                _uiState.update { uis ->
                    uis.copy(
                        loginState = LoginState.LoginRequired(reason = LoginRequiredReason.UNEXPECTED_ERROR),
                        isError = true
                    )
                }
            }
        }
    }

    fun onIntroductionNextClick() {
        val slide = uiState.value.introductionSlide
        if (slide < NUMBER_OF_SLIDES - 1) {
            _uiState.update { uis -> uis.copy(introductionSlide = slide + 1) }
        } else {
            _uiState.update { uis ->
                uis.copy(loginState = LoginState.LoginRequired(LoginRequiredReason.WAS_NOT_LOGGED_IN))
            }
        }
    }

    companion object {
        private const val NUMBER_OF_SLIDES = 3
    }
}
