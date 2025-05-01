/*
 * Copyright (c) 2024 Sensormatic Electronics, LLC.  All rights reserved.
 * Reproduction is forbidden without written approval of Sensormatic Electronics, LLC.
 */

package com.challenge.satellites.data.remote

import com.challenge.satellites.data.remote.satellite.model.SatelliteCollection
import retrofit2.http.GET
import retrofit2.http.Path

interface SatelliteApi {
    @GET("tle")
    suspend fun getCollection(): SatelliteCollection

    @GET("tle/{id}")
    suspend fun getSatelliteDetails(@Path("id") id: Int): SatelliteCollection.Member
}