package com.example.sliideapp.ui.compose

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sliideapp.data.model.User

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
