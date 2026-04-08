package com.example.heysports.data.sources

import android.content.SharedPreferences
import com.example.heysports.di.IoDispatcher
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import androidx.core.content.edit

class LocalDataSource @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    companion object {
        const val KEY_IS_LOGGED_IN = "isLoggedIn"
    }

    fun updateLoginStatus(isLoggedIn: Boolean) {
        sharedPreferences.edit { putBoolean(KEY_IS_LOGGED_IN, isLoggedIn) }
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }
}