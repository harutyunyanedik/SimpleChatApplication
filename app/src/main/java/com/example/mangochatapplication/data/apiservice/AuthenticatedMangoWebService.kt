package com.example.mangochatapplication.data.apiservice

import com.example.mangochatapplication.data.model.me.MeDto
import retrofit2.Response
import retrofit2.http.GET

interface AuthenticatedMangoWebService {

    @GET("/api/v1/users/me/")
    suspend fun me(): Response<MeDto?>
}