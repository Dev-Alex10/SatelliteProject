/*
 * Copyright (c) 2024 Sensormatic Electronics, LLC.  All rights reserved.
 * Reproduction is forbidden without written approval of Sensormatic Electronics, LLC.
 */

package com.challenge.satellites.ui.theme.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.satellites.data.SatelliteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: SatelliteRepository
) : ViewModel() {

    val retryTrigger = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val uiState = retryTrigger.onStart { emit(Unit) }
        .flatMapLatest {
            repository.getTleCollection()
                .map { satellites -> HomeViewState.Success(satellites) }
                .onStart<HomeViewState> { emit(HomeViewState.Loading) }
                .catch { error ->
                    emit(
                        HomeViewState.Error(
                            errorMessage = "An error occurred: ${error.message}"
                        )
                    )
                }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeViewState.Loading
        )

    fun retry() {
        retryTrigger.tryEmit(Unit)
    }
}