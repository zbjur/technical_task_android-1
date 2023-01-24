package com.example.sliideapp.data

import com.example.sliideapp.data.model.User
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitNetworkService @Inject constructor() : NetworkService {

    companion object {
        private const val tokenType = "Bearer"
        private const val accessToken = "b6bb09dd5e29c66cda4f340879275a4cc610f53f15330cc6aa8eb0605a3bbe2f"
        private const val urlAddress = "https://gorest.co.in/public/v2/"
    }

    class OAuthInterceptor(private val tokenType: String, private val accessToken: String) :
        Interceptor {
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            var request = chain.request()
            request =
                request.newBuilder().header("Authorization", "$tokenType $accessToken").build()

            return chain.proceed(request)
        }
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(urlAddress)

        .client(OkHttpClient.Builder()
            .addInterceptor(
                OAuthInterceptor(
                    tokenType,
                    accessToken
                )
            )
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    private val userService = retrofit.create(UserService::class.java)

    override suspend fun allUsers(): List<User> = userService.getAllUsers()

    override suspend fun addUser(user: User): User {
        return userService.addNewUser(user)
    }

    override suspend fun removeUser(userId: Int): Response<Unit>{
        return userService.removeUser(userId)
    }
}

interface UserService {
    @GET("users?page=1&per_page=20")
    suspend fun getAllUsers(): List<User>

    @POST("users")
    suspend fun addNewUser(@Body user: User): User

    @DELETE("users/{userId}")
    suspend fun removeUser(@Path("userId") userId: Int): Response<Unit>
}