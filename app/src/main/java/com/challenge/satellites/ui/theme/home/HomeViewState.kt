/*
 * Copyright (c) 2024 Sensormatic Electronics, LLC.  All rights reserved.
 * Reproduction is forbidden without written approval of Sensormatic Electronics, LLC.
 */

package com.challenge.satellites.ui.theme.home

import com.challenge.satellites.data.domain.model.Satellite

sealed interface HomeViewState {
    data object Loading : HomeViewState

    data class Success(
        val satellites: List<Satellite>
    ) : HomeViewState

    data class Error(val errorMessage: String) : HomeViewState

}