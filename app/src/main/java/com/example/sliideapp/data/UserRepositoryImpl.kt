package com.example.sliideapp.data

import com.example.sliideapp.data.model.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
class UserRepositoryImpl @Inject constructor(
    private val userService: RetrofitNetworkService
) : UserRepository {

    override fun getAllUsers(): Flow<List<User>> = flow { emit(userService.allUsers()) }

    override fun addUser(user: User): Flow<User> = flow { userService.addUser(user) }

    override suspend fun removeUser(userId: Int) {
        userService.removeUser(userId)
    }
}

