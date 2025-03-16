package model.login

import betterorioks.composeapp.generated.resources.Res
import betterorioks.composeapp.generated.resources.login_required_reason_bad_login_or_password
import betterorioks.composeapp.generated.resources.login_required_reason_session_expired
import betterorioks.composeapp.generated.resources.login_required_reason_unexpected_exception
import betterorioks.composeapp.generated.resources.login_required_reason_was_not_logged_in
import org.jetbrains.compose.resources.StringResource

enum class LoginRequiredReason(
    val stringRes: StringResource,
    val isUserError: Boolean
) {
    WAS_NOT_LOGGED_IN(
        stringRes = Res.string.login_required_reason_was_not_logged_in,
        isUserError = false
    ),
    SESSION_EXPIRED(
        stringRes = Res.string.login_required_reason_session_expired,
        isUserError = false
    ),
    BAD_LOGIN_OR_PASSWORD(
        stringRes = Res.string.login_required_reason_bad_login_or_password,
        isUserError = true
    ),
    UNEXPECTED_ERROR(
        stringRes = Res.string.login_required_reason_unexpected_exception,
        isUserError = true
    )
}

sealed interface LoginState {

    data object Loading : LoginState

    data object IntroductionRequired : LoginState

    data class LoginRequired(val reason: LoginRequiredReason) : LoginState

    data object Success : LoginState
}
