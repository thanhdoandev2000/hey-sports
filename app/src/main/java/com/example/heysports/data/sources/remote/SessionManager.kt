package com.example.heysports.data.sources.remote

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.status.SessionStatus
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first

@Singleton
class SessionManager @Inject constructor(
    private val db: SupabaseClient
) {
    suspend fun waitForSession() {
        db.auth.sessionStatus
            .filter { it !is SessionStatus.Initializing }
            .first()
    }

    fun isLoggedIn() = db.auth.currentSessionOrNull() != null
}