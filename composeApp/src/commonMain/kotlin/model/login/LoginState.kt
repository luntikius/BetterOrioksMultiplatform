package model.login

enum class LoginRequiredReason {
    WAS_NOT_LOGGED_IN,
    SESSION_EXPIRED,
    NO_INTERNET_LOGGING_IN,
    BAD_LOGIN_OR_PASSWORD_LOGGING_IN,
    UNEXPECTED_ERROR_LOGGING_IN
}

sealed interface LoginState {

    data object Loading : LoginState

    data object ReLoading : LoginState

    data class LoginRequired(val reason: LoginRequiredReason) : LoginState

    data object Success : LoginState
}
