/*
 * Copyright (c) 2024 Sensormatic Electronics, LLC.  All rights reserved.
 * Reproduction is forbidden without written approval of Sensormatic Electronics, LLC.
 */

package com.challenge.satellites.data

import com.challenge.satellites.data.domain.model.Satellite
import com.challenge.satellites.data.remote.satellite.model.Sort
import com.challenge.satellites.data.remote.satellite.model.SortDirection
import kotlinx.coroutines.flow.Flow

interface SatelliteRepository {
    fun getTleCollection(
        searchText: String = "",
        sortBy: Sort = Sort.NAME,
        sortDirection: SortDirection = SortDirection.ASC
    ): Flow<List<Satellite>>

    fun getSatelliteDetails(id: Int): Flow<Satellite>
}