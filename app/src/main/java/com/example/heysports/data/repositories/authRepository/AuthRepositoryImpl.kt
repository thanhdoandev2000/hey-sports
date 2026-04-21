package com.example.heysports.data.repositories.authRepository

import com.example.heysports.data.networks.NetworkResult
import com.example.heysports.data.sources.DataStoreManager
import com.example.heysports.data.sources.RemoteDataSource
import com.google.firebase.auth.FirebaseUser
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl @Inject constructor(
    private val dataStore: DataStoreManager,
    private val remoteDataSource: RemoteDataSource
) : AuthRepository {

    override suspend fun updateLoginStatus(isLoggedIn: Boolean) {
        dataStore.updateLoginStatus(isLoggedIn)
    }

    override fun isLoggedIn(): Flow<Boolean> = dataStore.isLoggedIn


    override fun isGettingStarted(): Flow<Boolean> = dataStore.isGettingStarted

    override suspend fun updateGettingStarted() {
        dataStore.updateGettingStarted()
    }

    override suspend fun login(email: String, password: String): NetworkResult<FirebaseUser?> {
        return remoteDataSource.login(email, password)
    }
}