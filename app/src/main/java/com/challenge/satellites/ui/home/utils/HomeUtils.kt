package com.challenge.satellites.ui.home.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.challenge.satellites.R
import com.challenge.satellites.data.domain.model.Sort
import com.challenge.satellites.data.domain.model.SortDirection
import com.challenge.satellites.ui.utils.FilterItem

@Composable
fun FilterDropDown(
    showSortFilter: Boolean = false,
    onDismissRequest: () -> Unit,
    onSortClick: (Sort, SortDirection) -> Unit,
    selectedSort: Sort,
    selectedSortSelection: SortDirection,
    onSelectSort: (Sort) -> Unit,
    onSelectSortSelection: (SortDirection) -> Unit,
    onFilterClick: () -> Unit
) {
    DropdownMenu(
        expanded = showSortFilter,
        onDismissRequest = onDismissRequest
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.65f)
                    .padding(start = 8.dp)
            ) {
                if (!LocalContext.current.isNetworkAvailable()) {
                    val dropDown = listOf(Sort.ID, Sort.NAME)
                    FilterItem(
                        entries = dropDown,
                        selectedSort = selectedSort
                    ) { onSelectSort(it) }
                } else {
                    FilterItem(
                        entries = Sort.entries,
                        selectedSort = selectedSort
                    ) { onSelectSort(it) }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp)
            ) {
                FilterItem(
                    entries = SortDirection.entries,
                    selectedSort = selectedSortSelection
                ) { onSelectSortSelection(it) }
            }
        }
        Row {
            Button({ onSortClick(selectedSort, selectedSortSelection) }) {
                Text(stringResource(R.string.sort))
            }
            Spacer(modifier = Modifier.weight(1f))
            TextButton(onClick = onFilterClick) {
                Text(stringResource(R.string.more_filters))
            }
        }
    }
}

fun Context.isNetworkAvailable(): Boolean {
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}