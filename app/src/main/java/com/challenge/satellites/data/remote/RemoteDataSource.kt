package com.challenge.satellites.data.remote

import com.challenge.satellites.data.domain.model.Eccentricity
import com.challenge.satellites.data.domain.model.Inclination
import com.challenge.satellites.data.domain.model.Period
import com.challenge.satellites.data.domain.model.Satellite
import com.challenge.satellites.data.domain.model.Sort
import com.challenge.satellites.data.domain.model.SortDirection

interface RemoteDataSource {
    suspend fun getTleCollection(
        searchText: String,
        sortBy: Sort,
        sortDirection: SortDirection,
        eccentricity: Eccentricity,
        inclination: Inclination,
        period: Period,
    ):  Result<List<Satellite>>

    suspend fun getSatelliteDetails(id: Int):  Result<Satellite>
}