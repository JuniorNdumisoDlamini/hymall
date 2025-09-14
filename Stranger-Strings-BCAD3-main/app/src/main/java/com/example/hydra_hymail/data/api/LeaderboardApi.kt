package com.example.hydra_hymail.data.api



import com.example.hydra_hymail.model.Badge
import retrofit2.Response
import retrofit2.http.GET

interface LeaderboardApi {
    @GET("leaderboard")
    suspend fun getLeaderboard(): Response<List<Badge>>
}
