package handlers

import android.app.Activity
import androidx.core.view.WindowCompat

class AndroidThemeHandler(private val activity: Activity): ThemeHandler {
    override fun setStatusBarTheme(isSystemInDarkTheme: Boolean) = activity.run {
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = !isSystemInDarkTheme
    }
}