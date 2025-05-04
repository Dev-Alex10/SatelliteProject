/*
 * Copyright (c) 2024 Sensormatic Electronics, LLC.  All rights reserved.
 * Reproduction is forbidden without written approval of Sensormatic Electronics, LLC.
 */

package com.challenge.satellites.data

import com.challenge.satellites.data.domain.model.Satellite
import com.challenge.satellites.data.local.LocalDataSource
import com.challenge.satellites.data.remote.RemoteDataSource
import com.challenge.satellites.data.remote.satellite.model.Sort
import com.challenge.satellites.data.remote.satellite.model.SortDirection
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SatelliteRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) : SatelliteRepository {
    private val mutex = Mutex()

    override fun getTleCollection(
        searchText: String,
        sortBy: Sort,
        sortDirection: SortDirection
    ): Flow<List<Satellite>> {
        if (sortBy == Sort.ID || sortBy == Sort.NAME) {
            return localDataSource.getSatellites(
                searchText = searchText,
                sort = sortBy,
                sortDirection = sortDirection
            ).onStart {
                fetchTleCollectionIfNeeded(searchText, sortBy, sortDirection)
            }
        }
        return flow {
            emit(remoteDataSource.getTleCollection(searchText, sortBy, sortDirection).getOrThrow())
        }
    }

    private suspend fun fetchTleCollectionIfNeeded(
        searchText: String,
        sortBy: Sort,
        sortDirection: SortDirection
    ) {
        return mutex.withLock {
            val localSatellites = localDataSource.getSatellites(
                searchText = searchText,
                sort = sortBy,
                sortDirection = sortDirection
            ).first()
            if (localSatellites.isNotEmpty()) {
                return@withLock
            }
            val remoteSatellites =
                remoteDataSource.getTleCollection(searchText, sortBy, sortDirection)
                    .getOrThrow()
            localDataSource.setSatellites(remoteSatellites)
        }
    }

    override fun getSatelliteDetails(id: Int): Flow<Satellite> {
        return localDataSource.getSatelliteById(id).onStart {
            fetchSatelliteDetailsIfNeeded(id)
        }
    }

    private suspend fun fetchSatelliteDetailsIfNeeded(id: Int) {
        return mutex.withLock {
            val localSatellite = localDataSource.getSatelliteById(id).firstOrNull()
            if (localSatellite != null) {
                return@withLock
            }
            val remoteSatellite = remoteDataSource.getSatelliteDetails(id).getOrThrow()
            localDataSource.setSatellite(remoteSatellite)
        }
    }

//    = flow {
//        emit(remoteDataSource.getSatelliteDetails(id).getOrThrow())
//    }

}