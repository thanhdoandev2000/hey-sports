package com.example.heysports

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HeySportsApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}