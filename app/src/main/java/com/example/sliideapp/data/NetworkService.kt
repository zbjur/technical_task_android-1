package com.example.sliideapp.data

import com.example.sliideapp.data.model.User

interface NetworkService {

    suspend fun allUsers(): List<User>

    suspend fun removeUser(userId: Int)

    suspend fun addUser(user: User): User
}