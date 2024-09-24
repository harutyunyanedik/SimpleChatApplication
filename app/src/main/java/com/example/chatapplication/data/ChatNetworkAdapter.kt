package com.example.chatapplication.data

import com.example.chatapplication.common.utils.net.model.ApiWrapper
import com.example.chatapplication.common.utils.net.parseResponse
import com.example.chatapplication.data.apiservice.AuthenticatedApiService
import com.example.chatapplication.data.apiservice.UnauthenticatedChatApiService
import com.example.chatapplication.data.model.checkcode.CheckAuthCodeDto
import com.example.chatapplication.data.model.checkcode.CheckAuthCodeRequest
import com.example.chatapplication.data.model.me.MeDto
import com.example.chatapplication.data.model.register.RegistrationDto
import com.example.chatapplication.data.model.register.RegistrationRequest
import com.example.chatapplication.data.model.sendcode.SendAuthCodeDto
import com.example.chatapplication.data.model.sendcode.SendAuthCodeRequest

class ChatNetworkAdapter(
    private val unauthenticatedChatApiService: UnauthenticatedChatApiService,
    private val authenticatedApiService: AuthenticatedApiService
) : ChatNetworkPort {
    override suspend fun sendAuthCode(phone: String): ApiWrapper<SendAuthCodeDto?> {
        return parseResponse {
            unauthenticatedChatApiService.sendAuthCode(SendAuthCodeRequest(phone))
        }
    }

    override suspend fun checkAuthCode(phone: String, code: String): ApiWrapper<CheckAuthCodeDto?> {
        return parseResponse {
            unauthenticatedChatApiService.checkAuthCode(CheckAuthCodeRequest(phone, code))
        }
    }

    override suspend fun register(name: String, phone: String, username: String): ApiWrapper<RegistrationDto?> {
        return parseResponse {
            unauthenticatedChatApiService.register(RegistrationRequest(phone, name, username))
        }
    }

    override suspend fun me(): ApiWrapper<MeDto?> {
        return parseResponse {
            authenticatedApiService.me()
        }
    }
}