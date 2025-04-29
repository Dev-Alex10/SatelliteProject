/*
 * Copyright (c) 2024 Sensormatic Electronics, LLC.  All rights reserved.
 * Reproduction is forbidden without written approval of Sensormatic Electronics, LLC.
 */

package com.challenge.satellites.data.remote

import com.challenge.satellites.data.remote.satellite.model.toDomain
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val satelliteApi: SatelliteApi) {

    suspend fun getTleCollection() = runCatching {
        satelliteApi.getCollection().member.map { it.toDomain() }
    }
}