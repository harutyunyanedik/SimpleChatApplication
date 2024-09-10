package com.example.mangochatapplication.data.boundresource.checkcode

import com.example.mangochatapplication.data.MangoChatNetworkPort
import com.example.mangochatapplication.data.model.checkcode.CheckAuthCodeDto
import com.example.mangochatapplication.common.utils.net.utils.ApiWrapper
import com.example.mangochatapplication.common.utils.net.utils.HttpBoundsResource
import com.example.mangochatapplication.domain.model.checkcode.CheckAuthCode

class CheckAuthCodeBoundResource(
    private val port: MangoChatNetworkPort,
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