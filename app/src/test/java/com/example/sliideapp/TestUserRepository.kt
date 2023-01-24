package com.example.sliideapp

import com.example.sliideapp.data.UserRepository
import com.example.sliideapp.data.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow

class TestUserRepository() : UserRepository {

    private val flow = MutableSharedFlow<List<User>>()

    suspend fun emitFakeUserList(userList: List<User>) {
        flow.emit(userList)
    }

    override fun getAllUsers(): Flow<List<User>> {
        return flow
    }

    override fun removeUser(userId: Int): Flow<Unit> {
        return flow {}
    }

    override fun addUser(user: User): Flow<User> {
        return flow {
            user
        }
    }
}