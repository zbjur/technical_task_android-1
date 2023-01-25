package com.example.sliideapp.ui

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

    private val _uiDialogState = MutableStateFlow<DialogState>(DialogState.DismissDialog)

    val uiDialogState: StateFlow<DialogState> = _uiDialogState

    init {
        loadUserList()
    }

    private fun loadUserList() {
        userRepository.getAllUsers()
            .map { UserUiState.Success(users = it) as UserUiState }
            .onStart { emit(UserUiState.Loading) }
            .onEach { _uiState.value = it }
            .catch { exception -> showErrorScreen(exception) }
            .launchIn(viewModelScope)
    }

    fun addUser(name: String, email: String, gender: String) {
        userRepository.addUser(mapToUser(name, email, gender))
            .map { UserUiState.Success(user = it) as UserUiState }
            .onStart { onDismissDialog() }
            .onEach { _uiState.value = it }
            .catch { exception -> showErrorScreen(exception) }
            .onCompletion { loadUserList() }
            .launchIn(viewModelScope)
    }

    fun removeUser(userId: Int) {
        userRepository.removeUser(userId)
            .onStart { onDismissDialog() }
            .catch { exception -> showErrorScreen(exception) }
            .onCompletion { loadUserList() }
            .launchIn(viewModelScope)
    }

    private fun showErrorScreen(exception: Throwable) {
        _uiState.value = UserUiState.Error(exception)
    }

    fun onDismissDialog() {
        _uiDialogState.value = DialogState.DismissDialog
    }

    fun onShowAddUserDialogClick() {
        _uiDialogState.value = DialogState.AddNewUserDialog
    }

    fun onShowDeleteUserDialogClick(userId: Int) {
        _uiDialogState.value = DialogState.DeleteUserDialog(userId)
    }

    sealed interface DialogState {
        object DismissDialog : DialogState
        object AddNewUserDialog : DialogState
        data class DeleteUserDialog(val userId: Int) : DialogState
    }

    sealed interface UserUiState {
        object Loading : UserUiState
        data class Success(val users: List<User>? = null, val user: User? = null) : UserUiState
        data class Error(val exception: Throwable? = null) : UserUiState
    }
}
