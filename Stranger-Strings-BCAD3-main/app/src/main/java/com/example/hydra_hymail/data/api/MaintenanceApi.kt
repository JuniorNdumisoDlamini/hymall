package com.example.hydra_hymail.data.api



import com.example.hydra_hymail.model.MaintenanceIssue
import retrofit2.Response
import retrofit2.http.*

interface MaintenanceApi {
    @GET("maintenance")
    suspend fun getAllIssues(): Response<List<MaintenanceIssue>>

    @POST("maintenance")
    suspend fun reportIssue(@Body issue: MaintenanceIssue): Response<MaintenanceIssue>

    @PUT("maintenance/{id}")
    suspend fun updateIssue(@Path("id") id: String, @Body issue: MaintenanceIssue): Response<MaintenanceIssue>

    @DELETE("maintenance/{id}")
    suspend fun deleteIssue(@Path("id") id: String): Response<Unit>
}
