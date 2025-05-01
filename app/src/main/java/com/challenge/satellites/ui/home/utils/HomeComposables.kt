package com.challenge.satellites.ui.home.utils

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.challenge.satellites.R
import com.challenge.satellites.data.remote.satellite.model.Sort
import com.challenge.satellites.data.remote.satellite.model.SortDirection
import kotlin.enums.EnumEntries

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
            Column(modifier = Modifier.fillMaxWidth(0.7f)) {
                DropDownItem(
                    entries = Sort.entries,
                    selectedSort = selectedSort
                ) { onSelectSort(it) }
            }
            Column(modifier = Modifier.fillMaxWidth()) {
                DropDownItem(
                    entries = SortDirection.entries,
                    selectedSort = selectedSortSelection
                ) {
                    onSelectSortSelection(
                        it
                    )
                }
            }
        }
        Button({ onSortClick(selectedSort, selectedSortSelection) }) {
            Text(stringResource(R.string.sort))
        }
    }
}

@Composable
fun <T : Enum<T>> DropDownItem(
    entries: EnumEntries<T>,
    selectedSort: T,
    onClickAction: (T) -> Unit
) {
    entries.forEach {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(it.name)
            Spacer(modifier = Modifier.weight(1f))
            RadioButton(selected = selectedSort == it, onClick = {
                onClickAction(it)
            })
        }
    }
}