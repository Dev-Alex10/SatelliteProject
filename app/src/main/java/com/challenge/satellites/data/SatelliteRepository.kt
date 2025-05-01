/*
 * Copyright (c) 2024 Sensormatic Electronics, LLC.  All rights reserved.
 * Reproduction is forbidden without written approval of Sensormatic Electronics, LLC.
 */

package com.challenge.satellites.data

import com.challenge.satellites.data.domain.model.Satellite
import kotlinx.coroutines.flow.Flow

interface SatelliteRepository {
    fun getTleCollection(): Flow<List<Satellite>>
    fun getSatelliteDetails(id: Int): Flow<Satellite>
}