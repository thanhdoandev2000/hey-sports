package com.example.heysports.di

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.example.heysports.BuildConfig
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CredentialModule {

    @Provides
    @Singleton
    fun provideGoogleIdOption(): GetGoogleIdOption =
        GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(BuildConfig.LOGIN_GOOGLE_KEY)
            .setAutoSelectEnabled(false)
            .build()

    @Provides
    @Singleton
    fun provideSignInWithGoogleOption(): GetSignInWithGoogleOption =
        GetSignInWithGoogleOption.Builder(BuildConfig.LOGIN_GOOGLE_KEY)
            .build()

    @Provides
    @Singleton
    fun provideGetCredentialRequest(
        googleIdOption: GetGoogleIdOption,
        signInWithGoogleOption: GetSignInWithGoogleOption
    ): GetCredentialRequest =
        GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .addCredentialOption(signInWithGoogleOption)
            .build()

    @Provides
    @Singleton
    fun provideCredentialManager(
        @ApplicationContext context: Context
    ): CredentialManager = CredentialManager.create(context)
}