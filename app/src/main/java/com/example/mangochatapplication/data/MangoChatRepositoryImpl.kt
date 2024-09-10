package com.example.mangochatapplication.data

import com.example.mangochatapplication.data.boundresource.checkcode.CheckAuthCodeBoundResource
import com.example.mangochatapplication.data.boundresource.sendcode.SendAuthCodeBoundResource
import com.example.mangochatapplication.domain.model.checkcode.CheckAuthCode
import com.example.mangochatapplication.domain.MangoChatRepository
import com.example.mangochatapplication.common.utils.Recourse
import com.example.mangochatapplication.data.boundresource.profile.MeBoundsResource
import com.example.mangochatapplication.data.boundresource.registration.RegistrationBoundsResource
import com.example.mangochatapplication.domain.model.profile.ProfileData
import com.example.mangochatapplication.domain.model.registration.Registration
import kotlinx.coroutines.flow.Flow

class MangoChatRepositoryImpl(private val port: MangoChatNetworkPort) : MangoChatRepository {

    override suspend fun sendAuthCode(phone: String): Flow<Recourse<Boolean>> {
        return SendAuthCodeBoundResource(port, phone).invoke()
    }

    override suspend fun checkAuthCode(phone: String, code: String): Flow<Recourse<CheckAuthCode>> {
        return CheckAuthCodeBoundResource(port, phone, code).invoke()
    }

    override suspend fun register(name: String, phone: String, username: String): Flow<Recourse<Registration>> {
        return RegistrationBoundsResource(port, name, phone, username).invoke()
    }

    override suspend fun me(): Flow<Recourse<ProfileData>> {
        return MeBoundsResource(port).invoke()
    }
}