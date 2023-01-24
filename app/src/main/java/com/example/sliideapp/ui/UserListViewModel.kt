package com.example.sliideapp.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sliideapp.data.UserRepository
import com.example.sliideapp.data.model.User
import com.example.sliideapp.data.model.User.Companion.mapToUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UserUiState>(UserUiState.Loading)

    val uiState: StateFlow<UserUiState> = _uiState

    init {
        loadUserList()
    }

    private fun loadUserList() {
        userRepository.getAllUsers()
            .map { UserUiState.Success(users = it) as UserUiState }
            .onStart { emit(UserUiState.Loading) }
            .onEach { _uiState.value = it }
            .catch { cause -> showErrorScreen(cause) }
            .launchIn(viewModelScope)
    }

    fun addUser(name: String, email: String, gender: String) {
        userRepository.addUser(mapToUser(name, email, gender))
            .onStart { onDismissDialog() }
            .onEach { _uiState.value = UserUiState.Success(user = it) }
            .catch { exception -> showErrorScreen(exception) }
            .onCompletion { loadUserList() }
            .launchIn(viewModelScope)
    }

    fun removeUser(userId: Int) {
        userRepository.removeUser(userId)
            .onStart { }
            .onEach { loadUserList() }
            .catch { exception -> showErrorScreen(exception) }
            .launchIn(viewModelScope)
    }

    private fun showErrorScreen(exception: Throwable) {
        _uiState.value = UserUiState.Error(exception)
    }

    var isDialogShown by mutableStateOf(false)
        private set

    fun onDismissDialog() {
        isDialogShown = false
    }

    fun onAddUserClick() {
        isDialogShown = true
    }

    sealed interface UserUiState {
        object Loading : UserUiState
        data class Success(val users: List<User>? = null, val user: User? = null) : UserUiState
        data class Error(val exception: Throwable? = null) : UserUiState
    }
}
