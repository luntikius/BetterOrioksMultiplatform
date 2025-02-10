package handlers

interface PermissionRequestHandler {
    fun requestNotificationPermission(onResult: (granted: Boolean) -> Unit)
}