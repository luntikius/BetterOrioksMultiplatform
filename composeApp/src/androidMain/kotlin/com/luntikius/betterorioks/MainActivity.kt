package com.luntikius.betterorioks

import App
import activityModule
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import handlers.PermissionRequestHandler
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActivityModule()
        setRussianLocale()
        enableEdgeToEdge()
        setContent {
            App()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        org.koin.core.context.unloadKoinModules(activityModule(this))
    }
}
