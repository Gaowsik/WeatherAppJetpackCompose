package com.codezync.weatherapp

import android.app.Application
import com.codezync.weatherapp.di.AppModule
import com.codezync.weatherapp.di.DatabaseModule
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WeatherApplication : Application() {
init {
    AppModule
    DatabaseModule
}
}