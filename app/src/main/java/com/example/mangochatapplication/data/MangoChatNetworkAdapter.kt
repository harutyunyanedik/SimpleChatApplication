package com.example.mangochatapplication.data

import com.example.mangochatapplication.common.utils.net.utils.ApiWrapper
import com.example.mangochatapplication.common.utils.net.utils.parseResponse
import com.example.mangochatapplication.data.apiservice.AuthenticatedMangoWebService
import com.example.mangochatapplication.data.apiservice.UnauthenticatedMangoChatWebService
import com.example.mangochatapplication.data.model.checkcode.CheckAuthCodeDto
import com.example.mangochatapplication.data.model.checkcode.CheckAuthCodeRequest
import com.example.mangochatapplication.data.model.me.MeDto
import com.example.mangochatapplication.data.model.register.RegistrationDto
import com.example.mangochatapplication.data.model.register.RegistrationRequest
import com.example.mangochatapplication.data.model.sendcode.SendAuthCodeDto
import com.example.mangochatapplication.data.model.sendcode.SendAuthCodeRequest

class MangoChatNetworkAdapter(
    private val unauthenticatedMangoChatWebService: UnauthenticatedMangoChatWebService,
    private val authenticatedMangoWebService: AuthenticatedMangoWebService
) : MangoChatNetworkPort {
    override suspend fun sendAuthCode(phone: String): ApiWrapper<SendAuthCodeDto?> {
        return parseResponse {
            unauthenticatedMangoChatWebService.sendAuthCode(SendAuthCodeRequest(phone))
        }
    }

    override suspend fun checkAuthCode(phone: String, code: String): ApiWrapper<CheckAuthCodeDto?> {
        return parseResponse {
            unauthenticatedMangoChatWebService.checkAuthCode(CheckAuthCodeRequest(phone, code))
        }
    }

    override suspend fun register(name: String, phone: String, username: String): ApiWrapper<RegistrationDto?> {
        return parseResponse {
            unauthenticatedMangoChatWebService.register(RegistrationRequest(phone, name, username))
        }
    }

    override suspend fun me(): ApiWrapper<MeDto?> {
        return parseResponse {
            authenticatedMangoWebService.me()
        }
    }
}