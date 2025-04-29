/*
 * Copyright (c) 2024 Sensormatic Electronics, LLC.  All rights reserved.
 * Reproduction is forbidden without written approval of Sensormatic Electronics, LLC.
 */

package com.challenge.satellites

import android.app.Application
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.challenge.satellites.nav.graph.SatelliteNavHost
import com.challenge.satellites.ui.theme.SatellitesTheme
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class SatelliteApplication @Inject constructor() : Application()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SatelliteApp() {
    SatellitesTheme {
        val navController = rememberNavController()
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStackEntry?.destination
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(text = currentDestination?.route ?: stringResource(R.string.satellite_app))
                    },
                    scrollBehavior = scrollBehavior
                )
            },
            content = {
                SatelliteNavHost(
                    navHostController = navController,
                    modifier = Modifier
                        .padding(it)
                        .nestedScroll(scrollBehavior.nestedScrollConnection)
                )
            }
        )
    }
}
