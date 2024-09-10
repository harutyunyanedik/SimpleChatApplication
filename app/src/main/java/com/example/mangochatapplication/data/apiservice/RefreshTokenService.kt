package com.example.mangochatapplication.data.apiservice

import com.example.mangochatapplication.data.model.refreshtoken.RefreshTokenDto
import com.example.mangochatapplication.data.model.refreshtoken.RefreshTokenRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RefreshTokenService {
    @POST("api/v1/users/refresh-token")
    suspend fun refreshToken(@Body refreshTokenRequest: RefreshTokenRequest): Response<RefreshTokenDto>
}