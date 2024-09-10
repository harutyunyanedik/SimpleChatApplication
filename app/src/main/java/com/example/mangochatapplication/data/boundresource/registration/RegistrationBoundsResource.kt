package com.example.mangochatapplication.data.boundresource.registration

import com.example.mangochatapplication.data.MangoChatNetworkPort
import com.example.mangochatapplication.data.model.register.RegistrationDto
import com.example.mangochatapplication.common.utils.net.utils.ApiWrapper
import com.example.mangochatapplication.common.utils.net.utils.HttpBoundsResource
import com.example.mangochatapplication.domain.model.registration.Registration

class RegistrationBoundsResource(
    private val port: MangoChatNetworkPort,
    private val name: String,
    private val phone: String,
    private val username: String
) : HttpBoundsResource<ApiWrapper<RegistrationDto?>, Registration>() {

    override suspend fun fetchFromNetwork(): ApiWrapper<RegistrationDto?> {
        return port.register(name, phone, username)
    }


    override fun processResponse(response: ApiWrapper<RegistrationDto?>?, result: Registration?): Registration? {
        return when (response) {
            is ApiWrapper.Success -> Registration(
                accessToken = response.data?.accessToken,
                refreshToken = response.data?.refreshToken,
                userId = response.data?.userId
            )

            else -> result
        }
    }
}