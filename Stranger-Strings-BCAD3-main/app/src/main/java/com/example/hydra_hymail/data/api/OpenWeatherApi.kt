package com.example.hydra_hymail.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {
    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): Response<Map<String, Any>>
}
