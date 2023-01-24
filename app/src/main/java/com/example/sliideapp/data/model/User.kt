package com.example.sliideapp.data.model

import com.google.gson.annotations.SerializedName
import kotlin.random.Random

data class User(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("status") val status: String
) {
    companion object {
        fun mapToUser(name: String, email: String, gender: String) =
            User(
                name = name,
                email = email,
                gender = gender,
                status = "active",
                id = Random.nextInt()
            )
    }
}
