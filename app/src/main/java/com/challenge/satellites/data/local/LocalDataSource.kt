/*
 * Copyright (c) 2024 Sensormatic Electronics, LLC.  All rights reserved.
 * Reproduction is forbidden without written approval of Sensormatic Electronics, LLC.
 */

package com.challenge.satellites.data.local

import com.challenge.satellites.data.domain.model.Satellite
import com.challenge.satellites.data.domain.model.Sort
import com.challenge.satellites.data.domain.model.SortDirection
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getSatellites(
        searchText: String,
        sort: Sort,
        sortDirection: SortDirection
    ): Flow<List<Satellite>>

    fun getSatelliteById(id: Int): Flow<Satellite>
    suspend fun setSatellites(satellites: List<Satellite>)
    suspend fun setSatellite(satellite: Satellite)
}