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
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.challenge.satellites.R
import com.challenge.satellites.data.remote.satellite.model.Sort
import com.challenge.satellites.data.remote.satellite.model.SortDirection

@Composable
fun FilterDropDown(
    showSortFilter: Boolean = false,
    onDismissRequest: () -> Unit,
    onSortClick: (Sort, SortDirection) -> Unit,
    selectedSort: Sort,
    selectedSortSelection: SortDirection,
    onSelectSort: (Sort) -> Unit,
    onSelectSortSelection: (SortDirection) -> Unit
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
                    DropDownItem(
                        entries = dropDown,
                        selectedSort = selectedSort
                    ) { onSelectSort(it) }
                } else {
                    DropDownItem(
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
                DropDownItem(
                    entries = SortDirection.entries,
                    selectedSort = selectedSortSelection
                ) { onSelectSortSelection(it) }
            }
        }
        Button({ onSortClick(selectedSort, selectedSortSelection) }) {
            Text(stringResource(R.string.sort))
        }
    }
}

@Composable
fun <T : Enum<T>> DropDownItem(
    entries: List<T>,
    selectedSort: T,
    onClickAction: (T) -> Unit
) {
    entries.forEach {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(it.name, modifier = Modifier.padding(end = 4.dp))
            Spacer(modifier = Modifier.weight(1f))
            RadioButton(selected = selectedSort == it, onClick = {
                onClickAction(it)
            })
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