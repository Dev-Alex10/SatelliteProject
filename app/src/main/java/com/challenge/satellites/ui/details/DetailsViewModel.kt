/*
 * Copyright (c) 2024 Sensormatic Electronics, LLC.  All rights reserved.
 * Reproduction is forbidden without written approval of Sensormatic Electronics, LLC.
 */

package com.challenge.satellites.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.satellites.data.SatelliteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: SatelliteRepository,
): ViewModel() {
    val uiState = repository.getSatelliteDetails(0)
        .map { satellite -> DetailsViewState.Success(satellite) }
        .onStart<DetailsViewState> { emit(DetailsViewState.Loading) }
        .catch { error ->
            emit(
                DetailsViewState.Error(
                    errorMessage = "An error occurred: ${error.message}"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = DetailsViewState.Loading
        )

    fun getSatelliteDetails(id: Int) =
        repository.getSatelliteDetails(id)
}