package com.example.sliideapp.ui

import com.example.sliideapp.data.UserRepository
import com.example.sliideapp.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UserListViewModelTest {

    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    class FakeDataSource : UserRepository {
        private val flow = MutableSharedFlow<List<User>>()
        suspend fun emit(value: List<User>) = flow.emit(value)

        override fun getAllUsers(): Flow<List<User>> {
            return flow
        }

        override suspend fun removeUser(userId: Int) {
        }

        override fun addUser(user: User): Flow<User> {

        }
    }

    val dataSource = FakeDataSource()

    private lateinit var viewModel: UserListViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = UserListViewModel(dataSource)
    }

    @Test
    fun uiState_whenInitialized_thenShowLoading() = runTest {

        assertEquals(UserListViewModel.UserUiState.Loading, viewModel.uiState.value)
    }

    @Test
    fun uiState_whenLoaded_thenShowListOfUsers() = runTest {
        val job = launch(UnconfinedTestDispatcher()) { viewModel.uiState.collect() }
        dataSource.emit(listOf())
        assertEquals(
            UserListViewModel.UserUiState.Success(emptyList<User>()),
            viewModel.uiState.value
        )
        job.cancel()
    }

    @Test
    fun uiState_whenErrorThrown_thenShowErrorInformation() = runTest {
        val job = launch(UnconfinedTestDispatcher()) { viewModel.uiState.collect() }
        dataSource.emit(listOf())
        assertEquals(
            UserListViewModel.UserUiState.Success(emptyList<User>()),
            viewModel.uiState.value
        )
        job.cancel()
    }

    @Test
    fun uiState_whenNewUserAdded_thenShowConfirmation() = runTest {
        val job = launch(UnconfinedTestDispatcher()) { viewModel.uiState.collect() }
        dataSource.emit(listOf())
        assertEquals(
            UserListViewModel.UserUiState.Success(emptyList<User>()),
            viewModel.uiState.value
        )
        job.cancel()
    }

    @Test
    fun uiState_whenUserRemoved_thenShowConfirmation() = runTest {
        val job = launch(UnconfinedTestDispatcher()) { viewModel.uiState.collect() }
        dataSource.emit(listOf())
        assertEquals(
            UserListViewModel.UserUiState.Success(emptyList<User>()),
            viewModel.uiState.value
        )
        job.cancel()
    }

}