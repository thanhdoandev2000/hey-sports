package com.example.heysports.data.sources.firebase

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultRegistryOwner
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.lifecycleScope
import com.example.heysports.BuildConfig
import com.example.heysports.cores.extensions.getValue
import com.example.heysports.cores.extensions.toObjectOrThrow
import com.example.heysports.data.models.dto.PersonInfoDto
import com.example.heysports.data.models.response.NetworkResult
import com.example.heysports.data.networks.safeApiCall
import com.example.heysports.di.IoDispatcher
import com.example.heysports.domain.models.PersonInfo
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class FirebaseAuthDataSource @Inject constructor(
    private val auth: FirebaseAuth,
    private val fireStore: FirebaseFirestore,
    private val credentialManager: CredentialManager,
    private val callbackManager: CallbackManager,
    private val loginManager: LoginManager,
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun login(email: String, password: String): NetworkResult<FirebaseUser?> {
        return safeApiCall(ioDispatcher) {
            auth.signInWithEmailAndPassword(email, password).await().user
        }
    }

    suspend fun syncUserToFirestore(firebaseUser: FirebaseUser) {
        val userRef = fireStore.collection("users").document(firebaseUser.uid)

        try {
            val snapshot = userRef.get().await()
            if (! snapshot.exists()) {
                val newUserInfo = PersonInfoDto(
                    uid = firebaseUser.uid,
                    name = firebaseUser.displayName.getValue(),
                    email = firebaseUser.email.getValue(),
                    photo = firebaseUser.photoUrl?.path.getValue(),
                    phone = firebaseUser.phoneNumber.getValue()
                )

                userRef.set(newUserInfo, SetOptions.merge()).await()
            }
        } catch (_: Exception) {
        }
    }

    suspend fun signInWithGoogle(context: Context): NetworkResult<FirebaseUser?> {
        return safeApiCall {
            val googleIdOption = GetGoogleIdOption.Builder().setFilterByAuthorizedAccounts(false)
                .setServerClientId(BuildConfig.LOGIN_GOOGLE_KEY).build()

            val request = GetCredentialRequest.Builder().addCredentialOption(googleIdOption).build()
            try {
                val result = credentialManager.getCredential(context, request)
                val credential = result.credential

                if (credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {

                    val googleIdTokenCredential =
                        GoogleIdTokenCredential.createFrom(credential.data)
                    val firebaseCredential = GoogleAuthProvider.getCredential(
                        googleIdTokenCredential.idToken, null
                    )
                    val user = auth.signInWithCredential(firebaseCredential).await().user
                    if (user != null) syncUserToFirestore(user)
                    user
                } else null
            } catch (e: GetCredentialCancellationException) {
                throw e
            } catch (e: GetCredentialException) {
                throw e
            }
        }
    }

    suspend fun signInWithFacebook(activity: ActivityResultRegistryOwner): NetworkResult<FirebaseUser?> {
        return safeApiCall {
            suspendCancellableCoroutine { continuation ->
                loginManager.logInWithReadPermissions(
                    activity,
                    callbackManager,
                    listOf("email", "public_profile")
                )

                loginManager.registerCallback(
                    callbackManager,
                    object : FacebookCallback<LoginResult> {
                        override fun onSuccess(result: LoginResult) {
                            val credential =
                                FacebookAuthProvider.getCredential(result.accessToken.token)
                            (activity as ComponentActivity).lifecycleScope.launch {
                                try {
                                    val user = auth.signInWithCredential(credential).await().user
                                    if (continuation.isActive) continuation.resume(user)
                                } catch (e: Exception) {
                                    if (continuation.isActive) continuation.resumeWithException(e)
                                }
                            }
                        }

                        override fun onCancel() {
                            if (continuation.isActive) continuation.resume(null)
                        }

                        override fun onError(error: FacebookException) {
                            if (continuation.isActive) continuation.resumeWithException(error)
                        }
                    }
                )
            }
        }
    }

    suspend fun isTokenValid(): Boolean {
        return try {
            auth.currentUser?.getIdToken(false)?.await() != null
        } catch (_: Exception) {
            false
        }
    }

    suspend fun createAccount(person: PersonInfoDto): NetworkResult<FirebaseUser> {
        return safeApiCall(ioDispatcher) {
            val user =
                auth.createUserWithEmailAndPassword(person.email, person.password).await().user
                    ?: throw Exception("User is null")
            try {
                fireStore.collection("users").document(user.uid).set(
                    mapOf(
                        "uid" to user.uid,
                        "name" to person.name,
                        "phone" to person.phone,
                        "email" to person.email,
                        "createdAt" to FieldValue.serverTimestamp()
                    )
                ).await()
                user
            } catch (e: Exception) {
                user.delete().await()
                throw e
            }
        }
    }

    suspend fun getPersonInfo(): NetworkResult<PersonInfo> {
        return safeApiCall(ioDispatcher) {
            val user = auth.currentUser ?: throw Exception("User is null")
            fireStore.collection("users")
                .document(user.uid)
                .get()
                .await().toObjectOrThrow<PersonInfoDto>().toDomain()
        }
    }
}