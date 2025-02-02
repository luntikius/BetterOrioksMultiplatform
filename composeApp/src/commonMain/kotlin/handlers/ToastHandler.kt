package handlers

interface ToastHandler {
    fun makeToast(message: String)
    fun makeShortToast(message: String)
}