/*
 * Copyright (c) 2024 Sensormatic Electronics, LLC.  All rights reserved.
 * Reproduction is forbidden without written approval of Sensormatic Electronics, LLC.
 */

package com.challenge.satellites.ui.details

import androidx.lifecycle.ViewModel
import com.challenge.satellites.data.SatelliteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: SatelliteRepository,
) : ViewModel() {

    fun getSatelliteDetails(id: Int) =
        repository.getSatelliteDetails(id)
}
