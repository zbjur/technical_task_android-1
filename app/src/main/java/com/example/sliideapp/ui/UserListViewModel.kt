package com.example.sliideapp.ui

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sliideapp.data.UserRepository
import com.example.sliideapp.data.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UserUiState>(UserUiState.Loading)

    val uiState: StateFlow<UserUiState> = _uiState

    init {
        viewModelScope.launch {
            userRepository.getAllUsers()
                .catch { exception -> _uiState.value = UserUiState.Error(exception) }
                .collect {
                    _uiState.value = UserUiState.Success(it)
                }
        }
    }

    fun removeUser(userId: Int) {
        viewModelScope.launch {
            try {
                userRepository.removeUser(userId)
            } catch (ex: Throwable) {
                _uiState.value = UserUiState.Error(ex)
            }
        }
    }

    fun addUser(name: String, email: String, gender: String) {
        onDismissDialog()
        viewModelScope.launch {
            userRepository.addUser(
                User(
                    name = name,
                    email = email,
                    status = "active",
                    gender = gender,
                    id = Random.nextInt()
                )
            ).catch { exception -> _uiState.value = UserUiState.Error(exception) }
                .collect()
        }
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
        data class Success(val users: List<User>) : UserUiState
        data class Error(val exception: Throwable? = null) : UserUiState
    }
}
