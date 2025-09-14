package com.example.hydra_hymail.data.api



import com.example.hydra_hymail.model.Settings
import retrofit2.Response
import retrofit2.http.*

interface SettingsApi {
    @GET("settings/{userId}")
    suspend fun getUserSettings(@Path("userId") userId: String): Response<Settings>

    @PUT("settings/{userId}")
    suspend fun updateUserSettings(@Path("userId") userId: String, @Body settings: Settings): Response<Settings>
}
