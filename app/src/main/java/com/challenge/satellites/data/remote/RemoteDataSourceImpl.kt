/*
 * Copyright (c) 2024 Sensormatic Electronics, LLC.  All rights reserved.
 * Reproduction is forbidden without written approval of Sensormatic Electronics, LLC.
 */

package com.challenge.satellites.data.remote

import com.challenge.satellites.data.domain.model.Eccentricity
import com.challenge.satellites.data.domain.model.Inclination
import com.challenge.satellites.data.domain.model.Period
import com.challenge.satellites.data.domain.model.Sort
import com.challenge.satellites.data.domain.model.SortDirection
import com.challenge.satellites.data.domain.model.toApiString
import com.challenge.satellites.data.remote.satellite.model.toDomain
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val satelliteApi: SatelliteApi) :
    RemoteDataSource {

    override suspend fun getTleCollection(
        searchText: String,
        sortBy: Sort,
        sortDirection: SortDirection,
        eccentricity: Eccentricity,
        inclination: Inclination,
        period: Period
    ) = runCatching {
        val eccentricityGte =
            if (eccentricity == Eccentricity.ECCENTRICITY_GTE) eccentricity.toApiString() else null
        val eccentricityLte =
            if (eccentricity == Eccentricity.ECCENTRICITY_LTE) eccentricity.toApiString() else null

        val inclinationLt =
            if (inclination == Inclination.POSIGRADE) inclination.toApiString() else null
        val inclinationGt =
            if (inclination == Inclination.RETROGADE) inclination.toApiString() else null

        val periodLt = if (period == Period.PERIOD_LT) period.toApiString() else null
        val periodGt = if (period == Period.PERIOD_GT) period.toApiString() else null

        satelliteApi.getCollection(
            searchText = searchText,
            sortBy = sortBy.toApiString(),
            sortDirection = sortDirection.toApiString(),
            eccentricityGte = eccentricityGte,
            eccentricityLte = eccentricityLte,
            inclinationLt = inclinationLt,
            inclinationGt = inclinationGt,
            periodLt = periodLt,
            periodGt = periodGt
        ).member.map { it.toDomain() }
    }

    override suspend fun getSatelliteDetails(id: Int) = runCatching {
        satelliteApi.getSatelliteDetails(id).toDomain()
    }
}