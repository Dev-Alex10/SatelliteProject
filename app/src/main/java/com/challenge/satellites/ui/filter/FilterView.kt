package com.challenge.satellites.ui.filter

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.challenge.satellites.R
import com.challenge.satellites.data.domain.model.Eccentricity
import com.challenge.satellites.data.domain.model.Inclination
import com.challenge.satellites.data.domain.model.Period
import com.challenge.satellites.data.domain.model.Sort
import com.challenge.satellites.data.domain.model.SortDirection
import com.challenge.satellites.ui.home.HomeFilterViewState
import com.challenge.satellites.ui.utils.FilterItemWithTitle

@Composable
fun FilterView(
    onApplyFilter: (HomeFilterViewState) -> Unit,
    onCancelClick: () -> Unit,
    homeFilterViewState: HomeFilterViewState,
    viewModel: FilterViewModel = hiltViewModel(),
) {
    viewModel.updateState(homeFilterViewState)
    FilterContent(
        onApplyFilter = onApplyFilter,
        onCancelClick = onCancelClick,
    )
}

@Composable
fun FilterContent(
    onApplyFilter: (HomeFilterViewState) -> Unit,
    onCancelClick: () -> Unit,
    viewModel: FilterViewModel = hiltViewModel(),
) {
    val filterViewState = viewModel.filterViewState.collectAsState().value

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        item {
            FilterItemWithTitle(
                title = stringResource(R.string.sort),
                entries = Sort.entries,
                selectedSort = filterViewState.selectedSort
            ) { selectedSort ->
                viewModel.updateState(selectedSort)
            }
        }
        item {
            FilterItemWithTitle(
                title = stringResource(R.string.sort_direction),
                entries = SortDirection.entries,
                selectedSort = filterViewState.selectedSortDirection
            ) { selectedSortSelection ->
                viewModel.updateState(selectedSortSelection)
            }
        }
        item {
            FilterItemWithTitle(
                title = stringResource(R.string.eccentricity),
                entries = Eccentricity.entries,
                selectedSort = filterViewState.selectedEccentricity
            ) { selectedEccentricity ->
                viewModel.updateState(selectedEccentricity)
            }
        }
        item {
            FilterItemWithTitle(
                title = stringResource(R.string.inclination),
                entries = Inclination.entries,
                selectedSort = filterViewState.selectedInclination
            ) { selectedInclination ->
                viewModel.updateState(selectedInclination)
            }
        }
        item {
            FilterItemWithTitle(
                title = stringResource(R.string.period),
                entries = Period.entries,
                selectedSort = filterViewState.selectedPeriod
            ) { selectedPeriod ->
                viewModel.updateState(selectedPeriod)
            }
        }
        item {
            Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                Button(
                    onClick = {
                        onApplyFilter(
                            HomeFilterViewState(
                                selectedSort = filterViewState.selectedSort,
                                selectedSortSelection = filterViewState.selectedSortDirection,
                                eccentricity = filterViewState.selectedEccentricity,
                                inclination = filterViewState.selectedInclination,
                                period = filterViewState.selectedPeriod
                            )
                        )
                    }
                ) {
                    Text(stringResource(R.string.apply))
                }

                Spacer(modifier = Modifier.weight(1f))

                Button(onClick = onCancelClick) {
                    Text(stringResource(R.string.cancel))
                }
            }
        }
    }
}
