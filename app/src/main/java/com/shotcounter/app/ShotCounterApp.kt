package com.shotcounter.app

import android.app.Application
import com.shotcounter.ui.UiConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

class ShotCounterApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@ShotCounterApp)
            loadKoinModules(
                listOf(
                    UiConfig.module
                )
            )
        }
    }
}