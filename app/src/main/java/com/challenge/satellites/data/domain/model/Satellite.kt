/*
 * Copyright (c) 2024 Sensormatic Electronics, LLC.  All rights reserved.
 * Reproduction is forbidden without written approval of Sensormatic Electronics, LLC.
 */

package com.challenge.satellites.data.domain.model

data class Satellite(
    val id: String,
    val satelliteId: Int,
    val name: String,
    val date: String,
    val line1: String,
    val line2: String,
)