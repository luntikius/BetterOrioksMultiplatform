package handlers

import android.app.Activity
import androidx.core.view.WindowCompat

class AndroidThemeHandler(private val activity: Activity) : ThemeHandler {
    override fun setStatusBarTheme(isSystemInDarkTheme: Boolean): Unit = activity.run {
        WindowCompat.getInsetsController(window, window.decorView).apply {
            isAppearanceLightStatusBars = !isSystemInDarkTheme
        }

    }
}