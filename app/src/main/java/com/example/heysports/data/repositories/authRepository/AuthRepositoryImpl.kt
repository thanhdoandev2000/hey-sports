package com.example.heysports.data.repositories.authRepository

import android.util.Log
import com.example.heysports.data.networks.NetworkResult
import com.example.heysports.data.sources.LocalDataSource
import com.example.heysports.data.sources.RemoteDataSource
import com.google.firebase.auth.FirebaseUser
import jakarta.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : AuthRepository {
    override fun updateLoginStatus(isLoggedIn: Boolean) {
        localDataSource.updateLoginStatus(isLoggedIn)
    }

    override suspend fun isLoggedIn(): Boolean {
        return localDataSource.isLoggedIn()
    }

    override suspend fun login(email: String, password: String): NetworkResult<FirebaseUser?> {
       return remoteDataSource.login(email, password)
    }
}