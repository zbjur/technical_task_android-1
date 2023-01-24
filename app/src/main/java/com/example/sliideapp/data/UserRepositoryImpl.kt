package com.example.sliideapp.data

import com.example.sliideapp.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
class UserRepositoryImpl @Inject constructor(
    private val userService: RetrofitNetworkService
) : UserRepository {

    override fun getAllUsers(): Flow<List<User>> = flow {
        emit(userService.allUsers())
    }
        .flowOn(Dispatchers.IO)

    override fun addUser(user: User): Flow<User> = flow<User> {
        userService.addUser(user)
    }
        .flowOn(Dispatchers.IO)

    override fun removeUser(userId: Int): Flow<Any> = flow {
        userService.removeUser(userId)
            .let {
                if (it.isSuccessful) {
                    emit(Unit)
                } else {
                    emit(HttpException(it))
                }
            }
    }.flowOn(Dispatchers.IO)
}

