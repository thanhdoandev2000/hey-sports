package com.example.heysports.data.repositories

import android.content.Context
import androidx.activity.result.ActivityResultRegistryOwner
import com.example.heysports.data.models.dto.UserDto
import com.example.heysports.data.models.response.NetworkResult
import com.example.heysports.domain.repositories.AuthRepository
import com.example.heysports.data.sources.remote.AuthDataSource
import com.example.heysports.data.sources.local.DataStoreManager
import com.example.heysports.domain.models.UserInfo
import com.google.firebase.auth.FirebaseUser
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepositoryImpl @Inject constructor(
    private val dataStore: DataStoreManager,
    private val remoteDataSource: AuthDataSource
) : AuthRepository {

    override fun isLoggedIn(): Flow<Boolean> {
        return flow {
            emit(remoteDataSource.isLoggedIn())
        }
    }

    override fun isGettingStarted(): Flow<Boolean> = dataStore.isGettingStarted

    override suspend fun updateGettingStarted() {
        dataStore.updateGettingStarted()
    }

    override suspend fun createAccount(person: UserDto): NetworkResult<String?> {
        return remoteDataSource.createAccount(person)
    }

    override suspend fun loginWithGoogle(context: Context): NetworkResult<String?> {
        return remoteDataSource.signInWithGoogle(context)
    }

    override suspend fun loginWithFacebook(activity: ActivityResultRegistryOwner): NetworkResult<FirebaseUser?> {
        return remoteDataSource.signInWithFacebook(activity)
    }

    override suspend fun getPersonInfo(): NetworkResult<UserInfo?> {
        return remoteDataSource.getPersonInfo()
    }

    override suspend fun singOut(): NetworkResult<Unit> {
        return remoteDataSource.signOut()
    }

    override suspend fun login(email: String, password: String): NetworkResult<String?> {
        return remoteDataSource.login(email, password)
    }
}