package com.example.chatapplication.data.apiservice

import com.example.chatapplication.data.model.checkcode.CheckAuthCodeDto
import com.example.chatapplication.data.model.checkcode.CheckAuthCodeRequest
import com.example.chatapplication.data.model.me.MeDto
import com.example.chatapplication.data.model.register.RegistrationDto
import com.example.chatapplication.data.model.register.RegistrationRequest
import com.example.chatapplication.data.model.sendcode.SendAuthCodeDto
import com.example.chatapplication.data.model.sendcode.SendAuthCodeRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ChatApiService {

    @POST("/api/v1/users/send-auth-code/")
    suspend fun sendAuthCode(@Body request: SendAuthCodeRequest): Response<SendAuthCodeDto?>

    @POST("/api/v1/users/check-auth-code/")
    suspend fun checkAuthCode(@Body request: CheckAuthCodeRequest): Response<CheckAuthCodeDto?>

    @POST("/api/v1/users/register/")
    suspend fun register(@Body request: RegistrationRequest): Response<RegistrationDto?>

    @GET("/api/v1/users/me/")
    suspend fun me(): Response<MeDto?>
}