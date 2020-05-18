package com.victor.dogbreeds

import android.app.Application
import com.victor.dogbreeds.di.Injection
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            modules(Injection.modules)
        }
    }
}