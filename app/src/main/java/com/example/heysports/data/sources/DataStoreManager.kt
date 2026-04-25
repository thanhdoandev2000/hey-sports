package com.example.heysports.data.sources

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


@Singleton
class DataStoreManager @Inject constructor(
    @param:ApplicationContext private val context: Context
) {
    companion object {
        private val KEY_IS_LOGGED_IN = booleanPreferencesKey("key_is_logged_in")
        private val KEY_IS_GETTING_STARTED = booleanPreferencesKey("key_is_getting_started")
    }

    private val Context.dataStore: DataStore<androidx.datastore.preferences.core.Preferences>
            by preferencesDataStore(name = "heysports_storages")

    val isLoggedIn: Flow<Boolean> = context.dataStore.data
        .map { preferences -> preferences[KEY_IS_LOGGED_IN] ?: false }

    suspend fun updateLoginStatus(isLoggedIn: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[KEY_IS_LOGGED_IN] = isLoggedIn
        }
    }

    val isGettingStarted: Flow<Boolean> = context.dataStore.data
        .map { preferences -> preferences[KEY_IS_GETTING_STARTED] ?: false }

    suspend fun updateGettingStarted() {
        context.dataStore.edit { preferences ->
            preferences[KEY_IS_GETTING_STARTED] = true
        }
    }
}