package com.example.chatapplication.data.boundresource.checkcode

import com.example.chatapplication.data.ChatNetworkPort
import com.example.chatapplication.data.model.checkcode.CheckAuthCodeDto
import com.example.chatapplication.common.utils.net.model.ApiWrapper
import com.example.chatapplication.common.utils.net.HttpBoundsResource
import com.example.chatapplication.domain.model.checkcode.CheckAuthCode

class CheckAuthCodeBoundResource(
    private val port: ChatNetworkPort,
    private val phone: String,
    private val code: String,
) : HttpBoundsResource<ApiWrapper<CheckAuthCodeDto?>, CheckAuthCode>() {

    override suspend fun fetchFromNetwork(): ApiWrapper<CheckAuthCodeDto?> {
        return port.checkAuthCode(phone, code)
    }

    override fun processResponse(response: ApiWrapper<CheckAuthCodeDto?>?, result: CheckAuthCode?): CheckAuthCode? {
        return when (response) {
            is ApiWrapper.Success -> CheckAuthCode(
                accessToken = response.data?.accessToken,
                isUserExists = response.data?.isUserExists,
                refreshToken = response.data?.refreshToken,
                userId = response.data?.userId
            )

            else -> result
        }
    }

    override fun saveNetworkResult(result: CheckAuthCode?) {
        super.saveNetworkResult(result)
        // todo store if needed
    }

    override fun fetchFromStorage(): CheckAuthCode? {
        // this is for fetching data from local storage
        return super.fetchFromStorage()
    }
}