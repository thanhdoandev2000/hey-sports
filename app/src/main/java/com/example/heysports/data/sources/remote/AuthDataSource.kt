package com.example.heysports.data.sources.remote

import android.content.Context
import androidx.activity.result.ActivityResultRegistryOwner
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.example.heysports.cores.extensions.getValue
import com.example.heysports.data.models.dto.UserDto
import com.example.heysports.data.models.response.NetworkResult
import com.example.heysports.data.networks.safeApiCall
import com.example.heysports.di.IoDispatcher
import com.example.heysports.domain.models.UserInfo
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.providers.builtin.IDToken
import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import javax.inject.Inject

class AuthDataSource @Inject constructor(
    private val db: SupabaseClient,
    private val credentialManager: CredentialManager,
    private val credentialRequest: GetCredentialRequest,
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun login(email: String, password: String): NetworkResult<String?> {
        return safeApiCall(ioDispatcher) {
            db.auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
            db.auth.currentUserOrNull()?.id
        }
    }

    suspend fun signInWithGoogle(context: Context): NetworkResult<String?> {
        return safeApiCall {
            val result = credentialManager.getCredential(
                request = credentialRequest,
                context = context
            )

            val googleCredential = GoogleIdTokenCredential
                .createFrom(result.credential.data)

            db.auth.signInWith(IDToken) {
                idToken = googleCredential.idToken
                provider = Google
            }
            db.auth.currentUserOrNull()?.id
        }
    }

    suspend fun isLoggedIn(): Boolean {
        db.auth.sessionStatus
            .filter { it !is SessionStatus.Initializing }
            .first()
        return db.auth.currentSessionOrNull() != null
    }

    suspend fun signOut(): NetworkResult<Unit> {
        return safeApiCall {
            db.auth.signOut()
        }
    }

    suspend fun signInWithFacebook(activity: ActivityResultRegistryOwner): NetworkResult<String?> {
        return safeApiCall {
            null
        }
    }

    suspend fun createAccount(person: UserDto): NetworkResult<String?> {
        return safeApiCall(ioDispatcher) {
            db.auth.signUpWith(Email) {
                email = person.email.getValue()
                password = person.password.getValue()
                data = buildJsonObject {
                    put("full_name", person.fullName)
                    put("phone_number", person.phoneNumber)
                }
            }
            db.auth.currentUserOrNull()?.id
        }
    }

    suspend fun getPersonInfo(): NetworkResult<UserInfo?> {
        return safeApiCall {
            db.from("users").select().decodeSingleOrNull<UserDto>()?.toDomain()
        }
    }
}