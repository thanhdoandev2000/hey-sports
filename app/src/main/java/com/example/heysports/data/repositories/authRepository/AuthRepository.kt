package com.example.heysports.data.repositories.authRepository

import com.example.heysports.data.networks.NetworkResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun updateLoginStatus(isLoggedIn: Boolean)

    fun isLoggedIn(): Flow<Boolean>

    suspend fun login(email: String, password: String): NetworkResult<FirebaseUser?>

    fun isGettingStarted(): Flow<Boolean>

    suspend fun updateGettingStarted()
}