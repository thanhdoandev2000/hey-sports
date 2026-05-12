package com.example.heysports.di

import com.example.heysports.domain.repositories.AuthRepository
import com.example.heysports.data.repositories.AuthRepositoryImpl
import com.example.heysports.data.repositories.MatchesRepositoryImpl
import com.example.heysports.data.repositories.UploadRepositoryImpl
import com.example.heysports.domain.repositories.MatchesRepository
import com.example.heysports.domain.repositories.UploadRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    abstract fun bindMatchesRepository(
        impl: MatchesRepositoryImpl
    ): MatchesRepository

    @Binds
    abstract fun bindUploadRepository(
        impl: UploadRepositoryImpl
    ): UploadRepository
}