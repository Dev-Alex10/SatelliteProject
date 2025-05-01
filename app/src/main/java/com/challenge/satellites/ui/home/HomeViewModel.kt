/*
 * Copyright (c) 2024 Sensormatic Electronics, LLC.  All rights reserved.
 * Reproduction is forbidden without written approval of Sensormatic Electronics, LLC.
 */

package com.challenge.satellites.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.satellites.data.SatelliteRepository
import com.challenge.satellites.data.remote.satellite.model.Sort
import com.challenge.satellites.data.remote.satellite.model.SortDirection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: SatelliteRepository
) : ViewModel() {

    private var debounceJob: Job? = null
    private val _filterState = MutableStateFlow(
        HomeFilterViewState(
            searchText = "",
            sort = Sort.NAME,
            sortDirection = SortDirection.ASC,
            selectedSort = Sort.NAME,
            selectedSortSelection = SortDirection.ASC
        )
    )
    val filterState: StateFlow<HomeFilterViewState> = _filterState
    private val retryTrigger = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val uiState = retryTrigger.onStart { emit(Unit) }.flatMapLatest {
        repository.getTleCollection(
            filterState.value.searchText,
            filterState.value.sort,
            filterState.value.sortDirection
        ).map { satellites -> HomeViewState.Success(satellites) }
            .onStart<HomeViewState> { emit(HomeViewState.Loading) }.catch { error ->
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

    fun onTextChanged(searchText: String) {
        _filterState.update {
            it.copy(searchText = searchText)
        }
        debounce(400) {
            retry()
        }
    }

    fun selectSort(selectedSort: Sort) = _filterState.update {
        it.copy(selectedSort = selectedSort)
    }

    fun selectSortSelection(selectedSortSelection: SortDirection) = _filterState.update {
        it.copy(selectedSortSelection = selectedSortSelection)
    }

    fun applySort(
        sortBy: Sort, sortDirection: SortDirection
    ) {
        _filterState.update {
            it.copy(sort = sortBy, sortDirection = sortDirection)
        }
        debounce(500) {
            retry()
        }
    }

    private fun debounce(debounceDelay: Long, action: suspend () -> Unit) {
        debounceJob?.cancel() // Cancel the previous job if it's still active
        debounceJob = viewModelScope.launch {
            delay(debounceDelay) // Wait for the debounce delay
            action() // Execute the action
        }
    }
}