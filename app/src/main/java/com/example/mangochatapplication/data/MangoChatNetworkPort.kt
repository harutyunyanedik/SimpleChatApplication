package com.example.mangochatapplication.data

import com.example.mangochatapplication.data.model.checkcode.CheckAuthCodeDto
import com.example.mangochatapplication.data.model.me.MeDto
import com.example.mangochatapplication.data.model.register.RegistrationDto
import com.example.mangochatapplication.data.model.sendcode.SendAuthCodeDto
import com.example.mangochatapplication.common.utils.net.utils.ApiWrapper

interface MangoChatNetworkPort {

    suspend fun sendAuthCode(phone: String): ApiWrapper<SendAuthCodeDto?>

    suspend fun checkAuthCode(phone: String, code: String) : ApiWrapper<CheckAuthCodeDto?>

    suspend fun register(name: String, phone: String, username: String): ApiWrapper<RegistrationDto?>

    suspend fun me(): ApiWrapper<MeDto?>
}