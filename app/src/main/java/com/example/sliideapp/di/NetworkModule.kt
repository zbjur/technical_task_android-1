package com.example.sliideapp.di

import com.example.sliideapp.data.NetworkService
import com.example.sliideapp.data.RetrofitNetworkService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface NetworkModule {

    @Binds
    fun RetrofitNetworkService.binds(): NetworkService
}
