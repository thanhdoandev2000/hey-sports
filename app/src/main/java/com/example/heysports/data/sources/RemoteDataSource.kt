package com.example.heysports.data.sources

import android.util.Log
import com.example.heysports.data.networks.NetworkResult
import com.example.heysports.data.networks.safeApiCall
import com.example.heysports.di.IoDispatcher
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val auth: FirebaseAuth,
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun login(email: String, password: String): NetworkResult<FirebaseUser?> {
        return safeApiCall(ioDispatcher) {
            auth.signInWithEmailAndPassword(email, password).await().user
        }
    }

    suspend fun isTokenValid(): Boolean {
        return try {
            auth
                .currentUser
                ?.getIdToken(true)
                ?.await() != null
        } catch (_: Exception) {
            false
        }
    }
}