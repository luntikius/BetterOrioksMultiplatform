package model.request

sealed interface ResponseState<out T> {

    data object NotStarted : ResponseState<Nothing>

    data class Loading(val reason: String? = null) : ResponseState<Nothing>

    data class Error(val error: String? = null) : ResponseState<Nothing>

    data class Success<T>(val result: T) : ResponseState<T>
}
