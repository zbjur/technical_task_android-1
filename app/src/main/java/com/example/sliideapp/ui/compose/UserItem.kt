package com.example.sliideapp.ui.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserItem(
    name: String,
    email: String,
    gender: String,
    status: String,
    id: Int,
    onClick: () -> Unit,
    onLongClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    itemSeparation: Dp = 16.dp
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .weight(1f)
                .clickable { onClick() }
                .padding(vertical = itemSeparation)
                .combinedClickable(onClick = {}, onLongClick = { onLongClick.invoke(id) })
        ) {

            Spacer(modifier = Modifier.width(16.dp))
            UserContent(name, email, gender, status)
        }
    }
}

@Composable
fun UserContent(
    name: String,
    email: String,
    gender: String,
    status: String,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Text(
            text = name,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(
                vertical = if (email.isEmpty()) 0.dp else 4.dp
            )
        )
        if (email.isNotEmpty()) {
            Text(
                text = email,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        if (gender.isNotEmpty()) {
            Text(
                text = gender,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        if (status.isNotEmpty()) {
            Text(
                text = status,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}