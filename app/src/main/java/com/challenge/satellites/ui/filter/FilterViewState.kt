package com.challenge.satellites.ui.filter

import com.challenge.satellites.data.domain.model.Eccentricity
import com.challenge.satellites.data.domain.model.Inclination
import com.challenge.satellites.data.domain.model.Period
import com.challenge.satellites.data.domain.model.Sort
import com.challenge.satellites.data.domain.model.SortDirection

data class FilterViewState(
    val selectedSort: Sort,
    val selectedSortDirection: SortDirection,
    val selectedEccentricity: Eccentricity = Eccentricity.NONE,
    val selectedInclination: Inclination = Inclination.NONE,
    val selectedPeriod: Period = Period.NONE,
)
