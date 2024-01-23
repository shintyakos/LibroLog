package com.example.liberolog

import android.app.Application
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication(override val workManagerConfiguration: Configuration) : Application(), Configuration.Provider {
    fun getWorkManagerConfiguration(): Configuration {
        TODO("Not yet implemented")
    }
}
