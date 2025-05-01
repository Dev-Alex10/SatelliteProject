/*
 * Copyright (c) 2024 Sensormatic Electronics, LLC.  All rights reserved.
 * Reproduction is forbidden without written approval of Sensormatic Electronics, LLC.
 */

package com.challenge.satellites.nav.destination

import androidx.navigation.NavType
import androidx.navigation.navArgument

interface SatelliteDestination {
    val route: String
    val name: String
}

object Home : SatelliteDestination {
    override val route = "Home"
    override val name = "Home"
}

object Details : SatelliteDestination {
    override val route = "Details/{id}"
    override val name = "Tle Details"
    val arguments = listOf(
        navArgument("id") {
            type = NavType.IntType
        }
    )
    fun route(id: Int) = "Details/$id"
}