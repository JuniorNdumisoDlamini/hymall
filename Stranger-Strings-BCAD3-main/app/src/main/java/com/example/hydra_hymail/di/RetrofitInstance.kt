package com.example.hydra_hymail.data.api

import com.example.hydra_hymail.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {

    // Toggle between local and production
    private const val USE_LOCAL = true

    private val BASE_URL = if (USE_LOCAL) {
        BuildConfig.LOCAL_BASE_URL
    } else {
        BuildConfig.PROD_BASE_URL
    }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // APIs
    val postApi: PostApi by lazy { retrofit.create(PostApi::class.java) }
    val userApi: UserApi by lazy { retrofit.create(UserApi::class.java) }
    val settingsApi: SettingsApi by lazy { retrofit.create(SettingsApi::class.java) }
    val maintenanceApi: MaintenanceApi by lazy { retrofit.create(MaintenanceApi::class.java) }
    val mapApi: MapApi by lazy { retrofit.create(MapApi::class.java) }
    val leaderboardApi: LeaderboardApi by lazy { retrofit.create(LeaderboardApi::class.java) }
    val cloudinaryApi: CloudinaryApi by lazy { retrofit.create(CloudinaryApi::class.java) }
    val googleMapsApi: GoogleMapsApi by lazy { retrofit.create(GoogleMapsApi::class.java) }
    val openWeatherApi: OpenWeatherApi by lazy { retrofit.create(OpenWeatherApi::class.java) }
}
