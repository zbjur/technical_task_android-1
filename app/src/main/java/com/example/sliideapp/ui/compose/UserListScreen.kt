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
                initMainScreen(uiState, modifier, onLongClick = {
                    viewModel.removeUser(it)
                })
            }
            is UserUiState.Error -> {
                Text(
                    text = (uiState as UserUiState.Error).exception.toString(),
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(vertical = 0.dp)
                )
            }
            UserUiState.Loading -> {
                Text(
                    text = "Here suppose to be a loading screen",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(vertical = 0.dp)
                )
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
private fun initMainScreen(
    uiState: UserUiState,
    modifier: Modifier,
    onLongClick: (Int) -> Unit
) {
    (uiState as UserUiState.Success).users?.let {
        UserListScreen(
            it,
            modifier = modifier,
            onLongClick = onLongClick
        )
    }
    uiState.user?.let {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = it.name,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(vertical = 0.dp)
            )
            Text(
                text = it.email,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(vertical = 0.dp)
            )
            Text(
                text = it.gender,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(vertical = 0.dp)
            )
            Text(
                text = it.status,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(vertical = 0.dp)
            )
        }
    }
}

@Composable
internal fun UserListScreen(
    users: List<User>,
    modifier: Modifier = Modifier, onLongClick: (Int) -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UserList(
            users,
            modifier = modifier, onLongClick = onLongClick
        )
    }
}

@Composable
fun UserList(
    users: List<User>, modifier: Modifier = Modifier, onLongClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp), contentPadding = PaddingValues(top = 8.dp)
    ) {
        users.forEach { user ->
            item {
                UserItem(
                    name = user.name,
                    email = user.email,
                    gender = user.gender,
                    status = user.status,
                    id = user.id,
                    onClick = { },
                    onLongClick = onLongClick,
                    modifier = modifier
                )
            }
        }

        item {
            Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
        }
    }
}
