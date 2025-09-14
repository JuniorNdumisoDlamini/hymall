package com.example.hydra_hymail.data.api


import com.example.hydra_hymail.model.Post
import retrofit2.Response
import retrofit2.http.*

interface PostApi {
    @GET("posts")
    suspend fun getPosts(): Response<List<Post>>

    @GET("posts/{id}")
    suspend fun getPostById(@Path("id") id: String): Response<Post>

    @POST("posts")
    suspend fun createPost(@Body post: Post): Response<Post>

    @PUT("posts/{id}")
    suspend fun updatePost(@Path("id") id: String, @Body post: Post): Response<Post>

    @DELETE("posts/{id}")
    suspend fun deletePost(@Path("id") id: String): Response<Unit>
}
