package com.example.chatapplication.domain.repository

import com.example.chatapplication.domain.model.checkcode.CheckAuthCode
import com.example.chatapplication.common.utils.net.model.Recourse
import com.example.chatapplication.domain.model.profile.ProfileData
import com.example.chatapplication.domain.model.registration.Registration
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    suspend fun sendAuthCode(phone: String): Flow<Recourse<Boolean>>

    suspend fun checkAuthCode(phone: String, code: String): Flow<Recourse<CheckAuthCode>>

    suspend fun register(name: String, phone: String, username: String): Flow<Recourse<Registration>>

    suspend fun me(): Flow<Recourse<ProfileData>>
}