package com.challenge.satellites.data.remote

import com.challenge.satellites.data.domain.model.Satellite
import com.challenge.satellites.data.remote.satellite.model.Sort
import com.challenge.satellites.data.remote.satellite.model.SortDirection

interface RemoteDataSource {
    suspend fun getTleCollection(
        searchText: String,
        sortBy: Sort,
        sortDirection: SortDirection
    ):  Result<List<Satellite>>

    suspend fun getSatelliteDetails(id: Int):  Result<Satellite>
}