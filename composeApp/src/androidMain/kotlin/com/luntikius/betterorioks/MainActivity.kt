package com.luntikius.betterorioks

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import di.sharedModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import platformModule

class MainActivity : ComponentActivity() {
    private fun startKoin() {
        startKoin {
            androidContext(this@MainActivity)
            modules(platformModule(), sharedModule())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startKoin()
        enableEdgeToEdge()
        setContent {
            App()
        }
    }
}
