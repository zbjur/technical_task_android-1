package com.example.sliideapp.data

import com.example.sliideapp.data.model.User
import retrofit2.Response

interface NetworkService {

    suspend fun allUsers(): List<User>

    suspend fun removeUser(userId: Int): Response<Unit>

    suspend fun addUser(user: User): User
}