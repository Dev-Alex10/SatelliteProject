/*
 * Copyright (c) 2024 Sensormatic Electronics, LLC.  All rights reserved.
 * Reproduction is forbidden without written approval of Sensormatic Electronics, LLC.
 */

package com.challenge.satellites.ui.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.challenge.satellites.data.domain.model.Satellite

@Composable
fun DetailsView(
    viewModel: DetailsViewModel,
    id: Int,
    modifier: Modifier
) {
    val satellite = viewModel.getSatelliteDetails(id = id).collectAsState(
        Satellite(name = "", satelliteId = id, line1 = "", line2 = "", date = "")
    ).value
    val details = listOf(
        "Name" to satellite.name,
        "Satellite ID" to satellite.satelliteId.toString(),
        "Date" to satellite.date,
        "Line 1" to satellite.line1,
        "Line 2" to satellite.line2
    )
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        items(details) { (label, value) ->
            Text(
                "$label:",
                modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = value,
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = 32.dp)
            )
        }
    }
}