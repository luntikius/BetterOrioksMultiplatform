package model.login

data class AuthData(
    val csrf: String,
    val orioksIdentity: String,
    val orioksSession: String
)
