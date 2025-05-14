package com.example.chatapplication.data.boundresource.sendcode

import com.example.chatapplication.data.ChatNetworkPort
import com.example.chatapplication.data.model.sendcode.SendAuthCodeDto
import com.example.chatapplication.common.utils.net.model.ApiWrapper
import com.example.chatapplication.common.utils.net.HttpBoundsResource
import com.example.chatapplication.common.utils.net.model.FetchPolicy

class SendAuthCodeBoundResource(private val port: ChatNetworkPort, private val phone: String) : HttpBoundsResource<ApiWrapper<SendAuthCodeDto?>, Boolean>() {

    override suspend fun fetchFromNetwork(): ApiWrapper<SendAuthCodeDto?> {
        return port.sendAuthCode(phone)
    }

    override fun processResponse(response: ApiWrapper<SendAuthCodeDto?>?, result: Boolean?): Boolean? {
        return when (response) {
            is ApiWrapper.Success -> response.data?.isSuccess
            else -> false
        }
    }

    override fun saveNetworkResult(result: Boolean?) {
        super.saveNetworkResult(result)
        // todo store if needed
    }

    override fun fetchFromStorage(): Boolean? {
        return super.fetchFromStorage()
        //todo fetch data from local storage if needed
    }
}