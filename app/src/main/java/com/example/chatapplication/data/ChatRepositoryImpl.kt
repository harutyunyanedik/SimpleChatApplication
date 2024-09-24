package com.example.chatapplication.data

import com.example.chatapplication.data.boundresource.checkcode.CheckAuthCodeBoundResource
import com.example.chatapplication.data.boundresource.sendcode.SendAuthCodeBoundResource
import com.example.chatapplication.domain.model.checkcode.CheckAuthCode
import com.example.chatapplication.domain.ChatRepository
import com.example.chatapplication.common.utils.net.model.Recourse
import com.example.chatapplication.data.boundresource.profile.MeBoundsResource
import com.example.chatapplication.data.boundresource.registration.RegistrationBoundsResource
import com.example.chatapplication.domain.model.profile.ProfileData
import com.example.chatapplication.domain.model.registration.Registration
import kotlinx.coroutines.flow.Flow

class ChatRepositoryImpl(private val port: ChatNetworkPort) : ChatRepository {

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