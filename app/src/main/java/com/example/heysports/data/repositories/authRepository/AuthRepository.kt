package com.example.heysports.data.repositories.authRepository

interface AuthRepository {

     fun updateLoginStatus(isLoggedIn: Boolean)

    suspend fun isLoggedIn(): Boolean
}