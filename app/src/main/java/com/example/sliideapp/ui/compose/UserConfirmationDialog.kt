package com.example.sliideapp.ui.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi


@ExperimentalLifecycleComposeApi
@Composable
fun UserConfirmationDialog(
    userId: Int, onDismiss: () -> Unit, onDeleteUser: (id: Int) -> Unit
) {
    AlertDialog(onDismissRequest = { onDismiss() },
        title = {
            Text(
                text = "Delete user?", style = MaterialTheme.typography.titleLarge
            )
        }, text = {

            Column(Modifier.verticalScroll(rememberScrollState())) {
                ConfirmationDetailsInfo()
            }
        }, confirmButton = {
            Text(text = "Delete",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clickable {
                        onDeleteUser(userId)
                    })
        }, dismissButton = {
            Text(text = "Cancel",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clickable { onDismiss() })

        })
}

@Composable
fun ConfirmationDetailsInfo() {
    Column {
        Text(text = "Are you sure you want to delete this user from the list?")
    }
}

