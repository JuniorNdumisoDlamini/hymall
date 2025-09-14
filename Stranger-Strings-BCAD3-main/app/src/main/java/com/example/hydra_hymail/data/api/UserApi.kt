package com.example.hydra_hymail.data.api



import com.example.hydra_hymail.model.User
import retrofit2.Response
import retrofit2.http.*

interface UserApi {

    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: String): Response<User>

    @GET("users/{id}")
    suspend fun getUserById(@Path("id") id: String): Response<User>

    @POST("users")
    suspend fun createUser(@Body user: User): Response<User>

    @PUT("users/{id}")
    suspend fun updateUser(@Path("id") id: String, @Body user: User): Response<User>

    @DELETE("users/{id}")
    suspend fun deleteUser(@Path("id") id: String): Response<Unit>
}
