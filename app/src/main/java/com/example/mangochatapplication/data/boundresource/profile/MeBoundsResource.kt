package com.example.mangochatapplication.data.boundresource.profile

import com.example.mangochatapplication.data.MangoChatNetworkPort
import com.example.mangochatapplication.data.model.me.MeDto
import com.example.mangochatapplication.common.utils.net.utils.ApiWrapper
import com.example.mangochatapplication.common.utils.net.utils.HttpBoundsResource
import com.example.mangochatapplication.domain.model.profile.ProfileData

class MeBoundsResource(
    private val port: MangoChatNetworkPort,
) : HttpBoundsResource<ApiWrapper<MeDto?>, ProfileData>() {

    override suspend fun fetchFromNetwork(): ApiWrapper<MeDto?> {
        return port.me()
    }


    override fun processResponse(response: ApiWrapper<MeDto?>?, result: ProfileData?): ProfileData? {
        return when (response) {
            is ApiWrapper.Success -> ProfileData(
                avatar = response.data?.profileData?.avatar,
                avatars = response.data?.profileData?.avatars,
                birthday = response.data?.profileData?.birthday,
                city = response.data?.profileData?.city,
                completedTask = response.data?.profileData?.completedTask,
                created = response.data?.profileData?.created,
                id = response.data?.profileData?.id,
                instagram = response.data?.profileData?.instagram,
                last = response.data?.profileData?.last,
                name = response.data?.profileData?.name,
                online = response.data?.profileData?.online,
                phone = response.data?.profileData?.phone,
                status = response.data?.profileData?.status,
                username = response.data?.profileData?.username,
                vk = response.data?.profileData?.vk,
            )

            else -> result
        }
    }
}