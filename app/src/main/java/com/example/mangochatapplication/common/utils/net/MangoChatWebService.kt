package com.example.mangochatapplication.common.utils.net

import com.example.mangochatapplication.data.model.checkcode.CheckAuthCodeDto
import com.example.mangochatapplication.data.model.checkcode.CheckAuthCodeRequest
import com.example.mangochatapplication.data.model.me.MeDto
import com.example.mangochatapplication.data.model.register.RegistrationDto
import com.example.mangochatapplication.data.model.register.RegistrationRequest
import com.example.mangochatapplication.data.model.sendcode.SendAuthCodeDto
import com.example.mangochatapplication.data.model.sendcode.SendAuthCodeRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MangoChatWebService {

    @POST("/api/v1/users/send-auth-code/")
    suspend fun sendAuthCode(@Body request: SendAuthCodeRequest): Response<SendAuthCodeDto?>

    @POST("/api/v1/users/check-auth-code/")
    suspend fun checkAuthCode(@Body request: CheckAuthCodeRequest): Response<CheckAuthCodeDto?>

    @POST("/api/v1/users/register/")
    suspend fun register(@Body request: RegistrationRequest): Response<RegistrationDto?>

    @GET("/api/v1/users/me/")
    suspend fun me(): Response<MeDto?>
}