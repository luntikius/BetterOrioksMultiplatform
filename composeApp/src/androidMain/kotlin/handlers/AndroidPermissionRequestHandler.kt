package handlers

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

class AndroidPermissionRequestHandler(private val activity: ComponentActivity) : PermissionRequestHandler {

    private val requestPermissionLauncher =
        activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            permissionCallback?.invoke(granted)
        }

    private var permissionCallback: ((Boolean) -> Unit)? = null

    override fun requestNotificationPermission(onResult: (granted: Boolean) -> Unit) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            onResult(true)
            return
        }

        val permission = Manifest.permission.POST_NOTIFICATIONS
        val isGranted = ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED

        if (isGranted) {
            onResult(true)
        } else {
            permissionCallback = onResult
            requestPermissionLauncher.launch(permission)
        }
    }
}

