package com.example.sliideapp.ui.compose

import SliideTopAppBar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.sliideapp.data.model.User
import com.example.sliideapp.ui.UserListViewModel
import com.example.sliideapp.ui.UserListViewModel.UserUiState

@OptIn(
    ExperimentalLifecycleComposeApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
internal fun MainUserScreen(
    modifier: Modifier = Modifier,
    viewModel: UserListViewModel = hiltViewModel(),
) {
    Column(Modifier.fillMaxSize()) {
        SliideTopAppBar(
            actionIcon = Icons.Rounded.Add,
            actionIconContentDescription = "Add new user",
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color.Transparent
            ),
            onActionClick = {
                viewModel.onAddUserClick()
            }
        )
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        when (uiState) {
            is UserUiState.Success -> {
                UserListScreen(
                    (uiState as UserUiState.Success).users,
                    modifier = modifier
                )
            }
            is UserUiState.Error -> {
                Text(
                    text = (uiState as UserUiState.Error).exception.toString(),
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(vertical = 0.dp))
            }
            UserUiState.Loading -> {
                Text(
                    text = "Here suppose to be a loading screen",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(vertical = 0.dp))
            }
        }
        if (viewModel.isDialogShown) {
            UserDialog(
                onSave = { name, email, gender ->
                    viewModel.addUser(name = name, email = email, gender = gender)
                }
            ) {
                viewModel.onDismissDialog()
            }
        }
    }
}

@Composable
internal fun UserListScreen(
    users: List<User>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UserList(
            users,
            modifier = modifier
        )
    }
}

@Composable
fun UserList(
    users: List<User>, modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp), contentPadding = PaddingValues(top = 8.dp)
    ) {
        users.forEach { user ->
            item() {
                UserItem(
                    name = user.name,
                    email = user.email,
                    gender = user.gender,
                    status = user.status,
                    onClick = { },
                    modifier = modifier
                )
            }
        }

        item {
            Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
        }
    }
}
