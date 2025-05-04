/*
 * Copyright (c) 2024 Sensormatic Electronics, LLC.  All rights reserved.
 * Reproduction is forbidden without written approval of Sensormatic Electronics, LLC.
 */

package com.challenge.satellites.data.remote

import com.challenge.satellites.data.remote.satellite.model.Sort
import com.challenge.satellites.data.remote.satellite.model.SortDirection
import com.challenge.satellites.data.remote.satellite.model.toApiString
import com.challenge.satellites.data.remote.satellite.model.toDomain
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val satelliteApi: SatelliteApi): RemoteDataSource {

    override suspend fun getTleCollection(searchText: String, sortBy: Sort, sortDirection: SortDirection) =
        runCatching {
            satelliteApi.getCollection(
                searchText = searchText,
                sortBy = sortBy.toApiString(),
                sortDirection = sortDirection.toApiString()
            ).member.map { it.toDomain() }
        }

    override suspend fun getSatelliteDetails(id: Int) = runCatching {
        satelliteApi.getSatelliteDetails(id).toDomain()
    }
}