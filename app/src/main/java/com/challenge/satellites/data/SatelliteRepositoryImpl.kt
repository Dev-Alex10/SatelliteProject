/*
 * Copyright (c) 2024 Sensormatic Electronics, LLC.  All rights reserved.
 * Reproduction is forbidden without written approval of Sensormatic Electronics, LLC.
 */

package com.challenge.satellites.data

import com.challenge.satellites.data.domain.model.Satellite
import com.challenge.satellites.data.local.LocalDataSource
import com.challenge.satellites.data.remote.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SatelliteRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) : SatelliteRepository {

    override fun getTleCollection(): Flow<List<Satellite>> = fetchTleCollection()
    fun fetchTleCollection() =
        flow {
            emit(remoteDataSource.getTleCollection().getOrThrow())
        }

    override fun getSatelliteDetails(id: Int): Flow<Satellite> =
        flow {
            emit(remoteDataSource.getSatelliteDetails(id).getOrThrow())
        }

}