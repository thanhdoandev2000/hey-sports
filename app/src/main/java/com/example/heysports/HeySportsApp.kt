package com.example.heysports

import android.app.Application
import android.content.res.Configuration
import androidx.hilt.work.HiltWorkerFactory
import com.google.firebase.inject.Provider
import dagger.hilt.android.HiltAndroidApp
import jakarta.inject.Inject

@HiltAndroidApp
class HeySportsApp : Application() {
    companion object {
        lateinit var instance: Application
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}