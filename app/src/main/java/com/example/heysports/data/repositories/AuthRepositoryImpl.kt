package com.example.heysports.data.repositories

import android.content.Context
import androidx.activity.result.ActivityResultRegistryOwner
import com.example.heysports.data.models.dto.PersonInfoDto
import com.example.heysports.data.models.response.NetworkResult
import com.example.heysports.domain.repositories.AuthRepository
import com.example.heysports.data.sources.firebase.FirebaseAuthDataSource
import com.example.heysports.data.sources.local.DataStoreManager
import com.example.heysports.domain.models.PersonInfo
import com.google.firebase.auth.FirebaseUser
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepositoryImpl @Inject constructor(
    private val dataStore: DataStoreManager,
    private val remoteDataSource: FirebaseAuthDataSource
) : AuthRepository {

    override fun isLoggedIn(): Flow<Boolean> {
        return flow {
            emit(remoteDataSource.isTokenValid())
        }
    }

    override fun isGettingStarted(): Flow<Boolean> = dataStore.isGettingStarted

    override suspend fun updateGettingStarted() {
        dataStore.updateGettingStarted()
    }

    override suspend fun createAccount(person: PersonInfoDto): NetworkResult<FirebaseUser> {
        return remoteDataSource.createAccount(person)
    }

    override suspend fun loginWithGoogle(context: Context): NetworkResult<FirebaseUser?> {
        return remoteDataSource.signInWithGoogle(context)
    }

    override suspend fun loginWithFacebook(activity: ActivityResultRegistryOwner): NetworkResult<FirebaseUser?> {
        return remoteDataSource.signInWithFacebook(activity)
    }

    override suspend fun getPersonInfo(): NetworkResult<PersonInfo> {
        return remoteDataSource.getPersonInfo()
    }

    override suspend fun login(email: String, password: String): NetworkResult<FirebaseUser?> {
        return remoteDataSource.login(email, password)
    }
}