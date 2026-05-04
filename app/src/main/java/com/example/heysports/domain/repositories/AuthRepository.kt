package com.example.heysports.domain.repositories

import android.content.Context
import androidx.activity.result.ActivityResultRegistryOwner
import com.example.heysports.data.models.dto.PersonInfoDto
import com.example.heysports.data.models.response.NetworkResult
import com.example.heysports.domain.models.PersonInfo
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun isLoggedIn(): Flow<Boolean>

    suspend fun login(email: String, password: String): NetworkResult<FirebaseUser?>

    fun isGettingStarted(): Flow<Boolean>

    suspend fun updateGettingStarted()

    suspend fun createAccount(person: PersonInfoDto): NetworkResult<FirebaseUser>

    suspend fun loginWithGoogle(context: Context): NetworkResult<FirebaseUser?>

    suspend fun loginWithFacebook(activity: ActivityResultRegistryOwner): NetworkResult<FirebaseUser?>

    suspend fun getPersonInfo(): NetworkResult<PersonInfo>
}