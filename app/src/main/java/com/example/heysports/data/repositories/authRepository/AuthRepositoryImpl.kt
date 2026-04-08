package com.example.heysports.data.repositories.authRepository

import com.example.heysports.data.sources.LocalDataSource
import jakarta.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val localDataSource: LocalDataSource) :
    AuthRepository {

    override fun updateLoginStatus(isLoggedIn: Boolean) {
        localDataSource.updateLoginStatus(isLoggedIn)
    }

    override suspend fun isLoggedIn(): Boolean {
        return localDataSource.isLoggedIn()
    }
}