package com.example.chatapplication.data.apiservice

import com.example.chatapplication.data.model.me.MeDto
import retrofit2.Response
import retrofit2.http.GET

interface AuthenticatedApiService {

    @GET("/api/v1/users/me/")
    suspend fun me(): Response<MeDto?>
}