package com.example.sliideapp.di

import com.example.sliideapp.data.UserRepository
import com.example.sliideapp.data.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun provideUserRepository(userRepository: UserRepositoryImpl): UserRepository
}