package com.example.heysports.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SourceModule {
//    @Binds
//    abstract fun bindLocalDataSource(
//        impl: LocalDataSource
//    ): LocalDataSource
//
//    @Binds
//    abstract fun bindRemoteDataSource(
//        impl: RemoteDataSource
//    ): RemoteDataSource
}