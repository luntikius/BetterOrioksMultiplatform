package model.login

data class AuthData(
    val csrf: String,
    val orioksIdentity: String,
    val orioksSession: String
) {
    val cookieString =
        "$AUTH_COOKIE_CSRF=$csrf; $AUTH_COOKIE_ORIOKS_SESSION=$orioksSession; $AUTH_COOKIE_ORIOKS_IDENTITY=$orioksIdentity"

    companion object {
        const val AUTH_COOKIE_CSRF = "_csrf"
        const val AUTH_COOKIE_ORIOKS_SESSION = "orioks_session"
        const val AUTH_COOKIE_ORIOKS_IDENTITY = "orioks_identity"
    }
}
