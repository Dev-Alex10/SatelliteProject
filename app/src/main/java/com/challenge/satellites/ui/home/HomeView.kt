/*
 * Copyright (c) 2024 Sensormatic Electronics, LLC.  All rights reserved.
 * Reproduction is forbidden without written approval of Sensormatic Electronics, LLC.
 */

package com.challenge.satellites.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.challenge.satellites.R
import com.challenge.satellites.ui.filter.FilterView
import com.challenge.satellites.ui.home.utils.FilterDropDown

@Composable
fun HomeView(
    viewModel: HomeViewModel = hiltViewModel(),
    onDetailsClick: (Int) -> Unit,
    modifier: Modifier,
) {
    val state = viewModel.uiState.collectAsState().value
    val filterState = viewModel.filterState.collectAsState().value
    val searchText = viewModel.searchTextInput.collectAsState().value
    var showSortFilter by remember { mutableStateOf(false) }
    var isFilterView by remember { mutableStateOf(false) }

    if (isFilterView) {
        return FilterView(
            {
                viewModel.applyAllFilters(it)
                isFilterView = false
                showSortFilter = false
            },
            homeFilterViewState = filterState,
            onCancelClick = {
                isFilterView = false
            })
    }
    when (state) {
        is HomeViewState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is HomeViewState.Error -> Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = state.errorMessage,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 8.dp),
                )
                Button(onClick = {
                    viewModel.retry()
                }) {
                    Text(stringResource(R.string.retry))
                }
            }
        }

        is HomeViewState.Success -> {
            // Display the list of satellites
            val satellites = state.satellites
            LazyColumn(modifier = modifier) {
                item {
                    TextField(
                        value = searchText,
                        onValueChange = viewModel::onTextChanged,
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = stringResource(R.string.search)
                            )
                        },
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    showSortFilter = !showSortFilter
                                },
                                content = {
                                    FilterDropDown(
                                        showSortFilter,
                                        onDismissRequest = {
                                            showSortFilter = false
                                        },
                                        onSortClick = { sort, sortDirection ->
                                            viewModel.applySort(sort, sortDirection)
                                            showSortFilter = false
                                        },
                                        selectedSort = filterState.selectedSort,
                                        onSelectSort = { selectedSort ->
                                            viewModel.selectSort(selectedSort)
                                        },
                                        selectedSortSelection = filterState.selectedSortSelection,
                                        onSelectSortSelection = { selectedSortSelection ->
                                            viewModel.selectSortSelection(selectedSortSelection)
                                        },
                                        onFilterClick = {
                                            isFilterView = true
                                        }
                                    )
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.List,
                                        contentDescription = stringResource(R.string.sort),
                                    )
                                },
                            )
                        },
                        placeholder = { Text(stringResource(R.string.search_by_placeholder)) },
                        singleLine = true
                    )
                }
                items(satellites) { satellite ->
                    Column(
                        modifier = Modifier
                            .clickable {
                                onDetailsClick(satellite.satelliteId)
                            }
                            .padding(8.dp)
                    ) {
                        Text(
                            text = satellite.name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Satellite: ${satellite.satelliteId}",
                            fontSize = 16.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        )
                        Text(
                            text = "${satellite.line1}\n${satellite.line2}",
                            fontSize = 16.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState())
                                .padding(horizontal = 16.dp)
                        )
                    }
                }
            }
        }
    }
}


