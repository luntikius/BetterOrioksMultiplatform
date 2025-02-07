package com.luntikius.betterorioks

import App
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import java.util.Locale

class MainActivity : ComponentActivity() {

    private fun setRussianLocale() {
        val locale = Locale("ru")
        Locale.setDefault(locale)

        val config = Configuration(this.applicationContext.resources.configuration)
        config.setLocale(locale)

        this.applicationContext.createConfigurationContext(config)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRussianLocale()
        enableEdgeToEdge()
        setContent {
            App()
        }
    }
}
