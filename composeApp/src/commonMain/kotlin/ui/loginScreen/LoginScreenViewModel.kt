package ui.loginScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import model.login.LoginRequiredReason
import model.login.LoginState

class LoginScreenViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginScreenUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getLoginData()
    }

    private fun getLoginData() {
        viewModelScope.launch {
            _uiState.update { uis -> uis.copy(loginState = LoginState.LoginRequired(reason = LoginRequiredReason.WAS_NOT_LOGGED_IN)) }
        }
    }

}