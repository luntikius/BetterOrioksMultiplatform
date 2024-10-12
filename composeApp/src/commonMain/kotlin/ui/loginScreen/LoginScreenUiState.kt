package ui.loginScreen

import model.login.LoginState

data class LoginScreenUiState(
    val loginState: LoginState = LoginState.Loading,
    val login: String = "",
    val password: String = "",
    val isError: Boolean = false
) {
    val isButtonEnabled = password.isNotBlank() && login.isNotBlank()
}
