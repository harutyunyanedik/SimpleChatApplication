package com.example.mangochatapplication.data.apiservice

import com.example.mangochatapplication.data.model.me.MeDto
import com.example.mangochatapplication.data.model.refreshtoken.RefreshTokenDto
import com.example.mangochatapplication.data.model.refreshtoken.RefreshTokenRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthenticatedMangoWebService {

    @GET("/api/v1/users/me/")
    suspend fun me(): Response<MeDto?>

    @POST("api/v1/users/refresh-token")
    suspend fun refreshToken(@Body refreshTokenRequest: RefreshTokenRequest): Response<RefreshTokenDto>
}