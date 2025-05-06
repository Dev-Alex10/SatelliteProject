/*
 * Copyright (c) 2024 Sensormatic Electronics, LLC.  All rights reserved.
 * Reproduction is forbidden without written approval of Sensormatic Electronics, LLC.
 */

package com.challenge.satellites.data

import com.challenge.satellites.data.domain.model.Eccentricity
import com.challenge.satellites.data.domain.model.Inclination
import com.challenge.satellites.data.domain.model.Period
import com.challenge.satellites.data.domain.model.Satellite
import com.challenge.satellites.data.domain.model.Sort
import com.challenge.satellites.data.domain.model.SortDirection
import com.challenge.satellites.data.local.LocalDataSource
import com.challenge.satellites.data.remote.RemoteDataSource
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
        sortDirection: SortDirection,
        eccentricity: Eccentricity,
        inclination: Inclination,
        period: Period,
    ): Flow<List<Satellite>> {
        if ((sortBy == Sort.ID || sortBy == Sort.NAME) && checkRemoteFiltersAreNone(eccentricity, inclination, period)) {
            return localDataSource.getSatellites(
                searchText = searchText, sort = sortBy, sortDirection = sortDirection
            ).onStart {
                fetchTleCollectionIfNeeded(
                    searchText = searchText,
                    sortBy = sortBy,
                    sortDirection = sortDirection,
                    eccentricity = eccentricity,
                    inclination = inclination,
                    period = period
                )
            }
        }

        return flow {
            emit(
                remoteDataSource.getTleCollection(
                    searchText = searchText,
                    sortBy = sortBy,
                    sortDirection = sortDirection,
                    eccentricity = eccentricity,
                    inclination = inclination,
                    period = period
                ).getOrThrow()
            )
        }
    }

    private suspend fun fetchTleCollectionIfNeeded(
        searchText: String,
        sortBy: Sort,
        sortDirection: SortDirection,
        eccentricity: Eccentricity,
        inclination: Inclination,
        period: Period,
    ) {
        return mutex.withLock {
            val localSatellites = localDataSource.getSatellites(
                searchText = searchText, sort = sortBy, sortDirection = sortDirection
            ).first()

            if (localSatellites.isNotEmpty()) {
                return@withLock
            }

            val remoteSatellites = remoteDataSource.getTleCollection(
                searchText = searchText,
                sortBy = sortBy,
                sortDirection = sortDirection,
                eccentricity = eccentricity,
                inclination = inclination,
                period = period
            ).getOrThrow()

            localDataSource.setSatellites(remoteSatellites)
        }
    }

    fun checkRemoteFiltersAreNone(
        eccentricity: Eccentricity,
        inclination: Inclination,
        period: Period
    ): Boolean =
        eccentricity == Eccentricity.NONE && inclination == Inclination.NONE && period == Period.NONE

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

}