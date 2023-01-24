package com.example.sliideapp.data

import com.example.sliideapp.data.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getAllUsers(): Flow<List<User>>

    fun removeUser(userId: Int): Flow<Any>

    fun addUser(user: User): Flow<User>
}