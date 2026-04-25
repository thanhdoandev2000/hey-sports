package com.example.heysports.data.repositories.authRepository

import android.content.Context
import androidx.activity.result.ActivityResultRegistryOwner
import com.example.heysports.data.models.entities.PersonEntity
import com.example.heysports.data.networks.NetworkResult
import com.example.heysports.data.sources.DataStoreManager
import com.example.heysports.data.sources.RemoteDataSource
import com.google.firebase.auth.FirebaseUser
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepositoryImpl @Inject constructor(
    private val dataStore: DataStoreManager,
    private val remoteDataSource: RemoteDataSource
) : AuthRepository {

    override suspend fun updateLoginStatus(isLoggedIn: Boolean) {
        dataStore.updateLoginStatus(isLoggedIn)
    }

    override fun isLoggedIn(): Flow<Boolean> {
        return flow {
            emit(remoteDataSource.isTokenValid())
        }
    }


    override fun isGettingStarted(): Flow<Boolean> = dataStore.isGettingStarted

    override suspend fun updateGettingStarted() {
        dataStore.updateGettingStarted()
    }

    override suspend fun createAccount(person: PersonEntity): NetworkResult<FirebaseUser> {
        return remoteDataSource.createAccount(person)
    }

    override suspend fun loginWithGoogle(context: Context): NetworkResult<FirebaseUser?> {
        return remoteDataSource.signInWithGoogle(context)
    }

    override suspend fun loginWithFacebook(activity: ActivityResultRegistryOwner): NetworkResult<FirebaseUser?> {
        return remoteDataSource.signInWithFacebook(activity)
    }

    override suspend fun login(email: String, password: String): NetworkResult<FirebaseUser?> {
        return remoteDataSource.login(email, password)
    }
}