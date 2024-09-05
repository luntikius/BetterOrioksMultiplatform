package com.luntikius.betterorioks

import android.app.Application
import di.sharedModule
import org.koin.android.ext.koin.androidContext
import platformModule

class BetterOrioksApplication : Application() {

    private fun startKoin() {
        org.koin.core.context.startKoin {
            androidContext(applicationContext)
            modules(platformModule(), sharedModule())
        }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin()
    }
}
