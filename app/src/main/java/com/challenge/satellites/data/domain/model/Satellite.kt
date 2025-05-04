/*
 * Copyright (c) 2024 Sensormatic Electronics, LLC.  All rights reserved.
 * Reproduction is forbidden without written approval of Sensormatic Electronics, LLC.
 */

package com.challenge.satellites.data.domain.model

import com.challenge.satellites.data.local.satellite.model.SatelliteEntity

data class Satellite(
    val satelliteId: Int,
    val name: String,
    val date: String,
    val line1: String,
    val line2: String
)

fun Satellite.toDatabaseEntity(): SatelliteEntity {
    return SatelliteEntity(
        satelliteId = satelliteId,
        name = name,
        date = date,
        line1 = line1,
        line2 = line2
    )
}