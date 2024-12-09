package model.user

sealed interface UserInfoState {
    data object Loading : UserInfoState
    data class Success(val userInfo: UserInfo) : UserInfoState
    data class Error(val exception: Exception) : UserInfoState
}