package com.studentapp.betterorioks

import App
import activityModule
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import handlers.PermissionRequestHandler
import kotlinx.serialization.json.Json
import model.BetterOrioksScreen
import org.koin.android.ext.android.inject
import java.util.Locale

class MainActivity : ComponentActivity() {

    private fun initActivityModule() {
        org.koin.core.context.loadKoinModules(activityModule(this))
        inject<PermissionRequestHandler>().value
    }

    private fun setRussianLocale() {
        val locale = Locale("ru")
        Locale.setDefault(locale)

        val config = Configuration(this.applicationContext.resources.configuration)
        config.setLocale(locale)

        this.applicationContext.createConfigurationContext(config)
    }

    private fun getOpenScreenAction(): BetterOrioksScreen? {
        val screenJson = intent.getStringExtra(EXTRA_SCREEN)
        return screenJson?.let {
            try {
                Json.decodeFromString(it)
            } catch (e: Exception) {
                null
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        initActivityModule()

        super.onCreate(savedInstanceState)

        setRussianLocale()

        setContent {
            App(
                openScreenAction = getOpenScreenAction()
            )
        }
    }

    companion object {
        const val EXTRA_SCREEN = "screen"
    }
}
