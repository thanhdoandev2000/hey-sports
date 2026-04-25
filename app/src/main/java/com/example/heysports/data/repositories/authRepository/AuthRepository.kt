package com.example.heysports.data.repositories.authRepository

import android.app.Activity
import com.example.heysports.data.models.entities.PersonEntity
import com.example.heysports.data.networks.NetworkResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import android.content.Context
import androidx.activity.result.ActivityResultRegistryOwner

interface AuthRepository {

    suspend fun updateLoginStatus(isLoggedIn: Boolean)

    fun isLoggedIn(): Flow<Boolean>

    suspend fun login(email: String, password: String): NetworkResult<FirebaseUser?>

    fun isGettingStarted(): Flow<Boolean>

    suspend fun updateGettingStarted()

    suspend fun createAccount(person: PersonEntity): NetworkResult<FirebaseUser>

    suspend fun loginWithGoogle(context: Context): NetworkResult<FirebaseUser?>

    suspend fun loginWithFacebook(activity: ActivityResultRegistryOwner): NetworkResult<FirebaseUser?>
}