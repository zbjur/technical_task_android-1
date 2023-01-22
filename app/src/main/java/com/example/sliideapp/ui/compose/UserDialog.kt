package com.example.sliideapp.ui.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi


@ExperimentalLifecycleComposeApi
@Composable
fun UserDialog(
    onSave: (name: String, email: String, gender: String) -> Unit,
    onDismiss: () -> Unit
) {
    val nameState = rememberSaveable { mutableStateOf("") }
    val emailState = remember { mutableStateOf("") }
    val genderState = remember { mutableStateOf("") }
    AlertDialog(onDismissRequest = { onDismiss() }, title = {
        Text(
            text = "Add new user", style = MaterialTheme.typography.titleLarge
        )
    }, text = {
        Divider()
        Column(Modifier.verticalScroll(rememberScrollState())) {
            UserDetailsInfo(nameState, emailState, genderState)
            Divider(Modifier.padding(top = 8.dp))
        }
    }, confirmButton = {
        Text(text = "Save",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .clickable {
                    onSave(
                        nameState.value,
                        emailState.value,
                        genderState.value
                    )
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailsInfo(
    nameState: MutableState<String>,
    emailState: MutableState<String>,
    genderState: MutableState<String>
) {
    Column {
        TextField(value = nameState.value, onValueChange = {
            nameState.value = it
        },
            label = {
                Text(text = "Name")
            })
        TextField(value = emailState.value,
            onValueChange = {
                emailState.value = it
            },
            label = { Text(text = "Email") })
        TextField(value = genderState.value,
            onValueChange = {
                genderState.value = it
            },
            label = { Text(text = "Gender") })
    }
}