/*
 * Copyright (c) 2024 Sensormatic Electronics, LLC.  All rights reserved.
 * Reproduction is forbidden without written approval of Sensormatic Electronics, LLC.
 */

package com.challenge.satellites.nav.destination

interface SatelliteDestination {
    val route: String
}

object Home : SatelliteDestination {
    override val route = "Home"
}