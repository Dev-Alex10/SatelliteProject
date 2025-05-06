package com.challenge.satellites.ui.filter

import androidx.lifecycle.ViewModel
import com.challenge.satellites.data.domain.model.Eccentricity
import com.challenge.satellites.data.domain.model.Inclination
import com.challenge.satellites.data.domain.model.Period
import com.challenge.satellites.data.domain.model.Sort
import com.challenge.satellites.data.domain.model.SortDirection
import com.challenge.satellites.ui.home.HomeFilterViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor() : ViewModel() {
    private val _filterViewState = MutableStateFlow(
        FilterViewState(
            selectedSort = Sort.NAME,
            selectedSortDirection = SortDirection.ASC
        )
    )
    val filterViewState: StateFlow<FilterViewState> = _filterViewState

    fun <T> updateState(stateToUpdate: T) {
        when (stateToUpdate) {
            is Sort -> _filterViewState.update { it.copy(selectedSort = stateToUpdate) }
            is SortDirection -> _filterViewState.update { it.copy(selectedSortDirection = stateToUpdate) }
            is Eccentricity -> _filterViewState.update { it.copy(selectedEccentricity = stateToUpdate) }
            is Inclination -> _filterViewState.update { it.copy(selectedInclination = stateToUpdate) }
            is Period -> _filterViewState.update { it.copy(selectedPeriod = stateToUpdate) }
            is HomeFilterViewState -> _filterViewState.update {
                it.copy(
                    selectedSort = stateToUpdate.selectedSort,
                    selectedSortDirection = stateToUpdate.selectedSortSelection
                )
            }

            else -> throw IllegalArgumentException("Unsupported type")
        }
    }
}
