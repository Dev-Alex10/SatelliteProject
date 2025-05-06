package com.challenge.satellites.ui.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun <T : Enum<T>> FilterItem(
    entries: List<T>,
    selectedSort: T?,
    onClickAction: (T) -> Unit
) {
    entries.forEach {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClickAction(it) }
        ) {
            Text(it.name, modifier = Modifier.padding(end = 4.dp))
            Spacer(modifier = Modifier.weight(1f))
            RadioButton(selected = selectedSort == it, onClick = {
                onClickAction(it)
            })
        }
    }
}

@Composable
fun <T : Enum<T>> FilterItemWithTitle(
    title: String,
    entries: List<T>,
    selectedSort: T?,
    onClickAction: (T) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 16.dp)
    ) {
        Text(
            title,
            modifier = Modifier.padding(end = 4.dp),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        FilterItem(
            entries = entries,
            selectedSort = selectedSort,
            onClickAction = onClickAction
        )
    }
}