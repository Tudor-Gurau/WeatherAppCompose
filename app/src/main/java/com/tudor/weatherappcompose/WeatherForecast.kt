package com.tudor.weatherappcompose

import android.app.Application
import com.tudor.weatherappcompose.di.appModule
import dagger.hilt.android.HiltAndroidApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class WeatherForecast : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@WeatherForecast)
            modules(appModule)
        }
    }
}