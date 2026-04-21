package com.example.heysports.data.repositories.authRepository

import com.example.heysports.data.networks.NetworkResult
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {

    fun updateLoginStatus(isLoggedIn: Boolean)

    suspend fun isLoggedIn(): Boolean

    suspend fun login(email: String, password: String): NetworkResult<FirebaseUser?>
}