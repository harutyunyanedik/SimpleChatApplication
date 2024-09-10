package com.example.mangochatapplication.data.boundresource.sendcode

import com.example.mangochatapplication.data.MangoChatNetworkPort
import com.example.mangochatapplication.data.model.sendcode.SendAuthCodeDto
import com.example.mangochatapplication.common.utils.net.utils.ApiWrapper
import com.example.mangochatapplication.common.utils.net.utils.HttpBoundsResource

class SendAuthCodeBoundResource(private val port: MangoChatNetworkPort, private val phone: String) : HttpBoundsResource<ApiWrapper<SendAuthCodeDto?>, Boolean>() {

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
        // this is for fetching data from local storage
        return super.fetchFromStorage()
    }
}