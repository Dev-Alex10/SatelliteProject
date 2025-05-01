/*
 * Copyright (c) 2024 Sensormatic Electronics, LLC.  All rights reserved.
 * Reproduction is forbidden without written approval of Sensormatic Electronics, LLC.
 */

package com.challenge.satellites.ui.details

import com.challenge.satellites.data.domain.model.Satellite

sealed interface DetailsViewState {
    data object Loading : DetailsViewState

    data class Success(
        val satellite: Satellite
    ) : DetailsViewState

    data class Error(val errorMessage: String) : DetailsViewState

}