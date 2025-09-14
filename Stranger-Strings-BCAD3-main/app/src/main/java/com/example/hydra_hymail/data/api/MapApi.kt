package com.example.hydra_hymail.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MapApi {
    @GET("maps/data")
    suspend fun getMallMapData(
        @Query("mallId") mallId: String
    ): Response<Map<String, Any>>
}
