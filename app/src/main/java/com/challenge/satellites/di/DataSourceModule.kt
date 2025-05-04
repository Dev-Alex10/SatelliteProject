package com.challenge.satellites.di

import com.challenge.satellites.data.local.LocalDataSource
import com.challenge.satellites.data.local.LocalDataSourceImpl
import com.challenge.satellites.data.remote.RemoteDataSource
import com.challenge.satellites.data.remote.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Singleton
    @Binds
    fun bindLocalDataSource(
        localDataSourceImpl: LocalDataSourceImpl
    ): LocalDataSource

    @Singleton
    @Binds
    fun bindRemoteDataSource(
        remoteDataSourceImpl: RemoteDataSourceImpl
    ): RemoteDataSource
}