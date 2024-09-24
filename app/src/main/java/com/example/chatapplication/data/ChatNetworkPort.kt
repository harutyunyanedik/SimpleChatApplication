package com.example.chatapplication.data

import com.example.chatapplication.data.model.checkcode.CheckAuthCodeDto
import com.example.chatapplication.data.model.me.MeDto
import com.example.chatapplication.data.model.register.RegistrationDto
import com.example.chatapplication.data.model.sendcode.SendAuthCodeDto
import com.example.chatapplication.common.utils.net.model.ApiWrapper

interface ChatNetworkPort {

    suspend fun sendAuthCode(phone: String): ApiWrapper<SendAuthCodeDto?>

    suspend fun checkAuthCode(phone: String, code: String) : ApiWrapper<CheckAuthCodeDto?>

    suspend fun register(name: String, phone: String, username: String): ApiWrapper<RegistrationDto?>

    suspend fun me(): ApiWrapper<MeDto?>
}