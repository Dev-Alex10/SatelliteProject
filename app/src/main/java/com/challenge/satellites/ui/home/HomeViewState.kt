/*
 * Copyright (c) 2024 Sensormatic Electronics, LLC.  All rights reserved.
 * Reproduction is forbidden without written approval of Sensormatic Electronics, LLC.
 */

package com.challenge.satellites.ui.home

import com.challenge.satellites.data.domain.model.Eccentricity
import com.challenge.satellites.data.domain.model.Inclination
import com.challenge.satellites.data.domain.model.Period
import com.challenge.satellites.data.domain.model.Satellite
import com.challenge.satellites.data.domain.model.Sort
import com.challenge.satellites.data.domain.model.SortDirection

sealed interface HomeViewState {
    data object Loading : HomeViewState

    data class Success(
        val satellites: List<Satellite>,
        val searchText: String = "",
        val sort: Sort = Sort.NAME,
        val sortDirection: SortDirection = SortDirection.ASC
    ) : HomeViewState

    data class Error(val errorMessage: String) : HomeViewState

}

data class HomeFilterViewState(
    val searchText: String = "",
    val sort: Sort = Sort.NAME,
    val selectedSort: Sort = sort,
    val sortDirection: SortDirection = SortDirection.ASC,
    val selectedSortSelection: SortDirection = sortDirection,
    val eccentricity: Eccentricity = Eccentricity.NONE,
    val inclination: Inclination = Inclination.NONE,
    val period: Period = Period.NONE,
)