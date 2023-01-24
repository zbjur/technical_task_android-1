package com.example.sliideapp.ui

import com.example.sliideapp.TestUserRepository
import com.example.sliideapp.data.model.User
import kotlinx.coroutines.Dispatchers
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

    private val dataSource = TestUserRepository()

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
        dataSource.emitFakeUserList(testOutputUsers)

        val job = launch(UnconfinedTestDispatcher()) { viewModel.uiState.collect() }

        assertEquals(
            UserListViewModel.UserUiState.Success(testOutputUsers),
            (viewModel.uiState.value as UserListViewModel.UserUiState.Success)
        )
        job.cancel()
    }

    @Test
    fun uiState_whenErrorThrown_thenShowErrorInformation() = runTest {
        dataSource.emitFakeUserList(testOutputUsers)
        val job = launch(UnconfinedTestDispatcher()) { viewModel.uiState.collect() }
        assertEquals(
            UserListViewModel.UserUiState.Success(testOutputUsers), viewModel.uiState.value
        )
        job.cancel()
    }

    @Test
    fun uiState_whenNewUserAdded_thenShowConfirmation() = runTest {
        dataSource.addUser(testOutputUser)
        val job = launch(UnconfinedTestDispatcher()) { viewModel.uiState.collect() }
        assertEquals(testOutputUser, viewModel.uiState.value)
        job.cancel()
    }

    @Test
    fun uiState_whenUserRemoved_thenShowConfirmation() = runTest {
        val job = launch(UnconfinedTestDispatcher()) { viewModel.uiState.collect() }
        dataSource.emitFakeUserList(listOf())
        assertEquals(
            UserListViewModel.UserUiState.Success(emptyList<User>()),
            viewModel.uiState.value
        )
        job.cancel()
    }

    private val testOutputUser = User(
        id = 1,
        name = "Frank",
        email = "frank@dot.com",
        gender = "male",
        status = "active"
    )

    private val testOutputUsers = listOf(
        User(
            id = 1,
            name = "Frank",
            email = "frank@dot.com",
            gender = "male",
            status = "active"
        ), User(
            id = 2,
            name = "Joseph",
            email = "joseph@dot.com",
            gender = "male",
            status = "active"
        ),
        User(
            id = 3,
            name = "Bo",
            email = "bo@dot.com",
            gender = "male",
            status = "active"
        ),

        User(
            id = 4,
            name = "Willy",
            email = "willy@dot.com",
            gender = "male",
            status = "active"
        )
    )

}