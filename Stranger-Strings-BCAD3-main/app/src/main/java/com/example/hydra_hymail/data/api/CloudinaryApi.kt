package com.example.hydra_hymail.data.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface CloudinaryApi {
    @Multipart
    @POST("image/upload")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part,
        @Part("upload_preset") preset: RequestBody
    ): Response<Map<String, Any>>
}
