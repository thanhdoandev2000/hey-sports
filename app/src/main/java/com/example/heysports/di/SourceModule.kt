package com.example.heysports.di

import com.example.heysports.data.sources.LocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SourceModule {
    @Binds
    abstract fun bindLocalDataSource(
        impl: LocalDataSource
    ): LocalDataSource
}