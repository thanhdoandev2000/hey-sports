package com.example.heysports.domain.repositories

import android.content.Context
import androidx.activity.result.ActivityResultRegistryOwner
import com.example.heysports.data.models.dto.UserDto
import com.example.heysports.data.models.response.NetworkResult
import com.example.heysports.domain.models.UserInfo
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun isLoggedIn(): Flow<Boolean>

    suspend fun login(email: String, password: String): NetworkResult<String?>

    fun isGettingStarted(): Flow<Boolean>

    suspend fun updateGettingStarted()

    suspend fun createAccount(person: UserDto): NetworkResult<String?>

    suspend fun loginWithGoogle(context: Context): NetworkResult<String?>

    suspend fun loginWithFacebook(activity: ActivityResultRegistryOwner): NetworkResult<FirebaseUser?>

    suspend fun getPersonInfo(): NetworkResult<UserInfo?>

    suspend fun singOut(): NetworkResult<Unit>
}