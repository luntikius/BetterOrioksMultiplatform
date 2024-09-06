package ui.loginScreen

import model.login.LoginState

data class LoginScreenUiState (
    val loginState: LoginState = LoginState.Loading
)