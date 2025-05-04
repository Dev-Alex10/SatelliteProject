package com.challenge.satellites.di

import android.content.Context
import androidx.room.Room
import com.challenge.satellites.data.local.SatelliteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    fun provideSatelliteDao(database: SatelliteDatabase) = database.satelliteDao()

    @Provides
    @Singleton
    fun provideSatelliteDatabase(@ApplicationContext appContext: Context): SatelliteDatabase {
        return Room.databaseBuilder(
            appContext,
            SatelliteDatabase::class.java,
            "satellite_database"
        ).build()
    }
}