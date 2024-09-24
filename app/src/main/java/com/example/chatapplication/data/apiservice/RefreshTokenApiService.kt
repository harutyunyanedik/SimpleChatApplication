package com.example.chatapplication.data.apiservice

import com.example.chatapplication.data.model.refreshtoken.RefreshTokenDto
import com.example.chatapplication.data.model.refreshtoken.RefreshTokenRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RefreshTokenApiService {
    @POST("api/v1/users/refresh-token/")
    suspend fun refreshToken(@Body refreshTokenRequest: RefreshTokenRequest): Response<RefreshTokenDto>
}