/*
 * Copyright (c) 2024 Sensormatic Electronics, LLC.  All rights reserved.
 * Reproduction is forbidden without written approval of Sensormatic Electronics, LLC.
 */

package com.challenge.satellites.data.remote

import com.challenge.satellites.data.remote.satellite.model.SatelliteCollection
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SatelliteApi {
    @GET("tle/")
    suspend fun getCollection(
        @Query(value = "search") searchText: String,
        @Query(value = "sort") sortBy: String,
        @Query(value = "sort-dir") sortDirection: String,
        @Query(value = "eccentricity[gte]") eccentricityGte: String? = null,
        @Query(value = "eccentricity[lte]") eccentricityLte: String? = null,
        @Query(value = "inclination[lt]") inclinationLt: String? = null,
        @Query(value = "inclination[gt]") inclinationGt: String? = null,
        @Query(value = "period[lt]") periodLt: String? = null,
        @Query(value = "period[gt]") periodGt: String? = null,
    ): SatelliteCollection

    @GET("tle/{id}")
    suspend fun getSatelliteDetails(@Path("id") id: Int): SatelliteCollection.Member
}