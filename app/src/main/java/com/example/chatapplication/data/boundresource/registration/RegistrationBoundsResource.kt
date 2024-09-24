package com.example.chatapplication.data.boundresource.registration

import com.example.chatapplication.data.ChatNetworkPort
import com.example.chatapplication.data.model.register.RegistrationDto
import com.example.chatapplication.common.utils.net.model.ApiWrapper
import com.example.chatapplication.common.utils.net.HttpBoundsResource
import com.example.chatapplication.domain.model.registration.Registration

class RegistrationBoundsResource(
    private val port: ChatNetworkPort,
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