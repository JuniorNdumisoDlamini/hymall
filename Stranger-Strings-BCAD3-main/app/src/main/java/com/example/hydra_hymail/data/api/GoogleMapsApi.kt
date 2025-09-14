package com.example.hydra_hymail.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleMapsApi {
    @GET("geocode/json")
    suspend fun getAddressFromCoordinates(
        @Query("latlng") latlng: String,
        @Query("key") apiKey: String
    ): Response<Map<String, Any>>

    @GET("place/nearbysearch/json")
    suspend fun getNearbyPlaces(
        @Query("location") location: String,
        @Query("radius") radius: Int,
        @Query("key") apiKey: String
    ): Response<Map<String, Any>>
}
