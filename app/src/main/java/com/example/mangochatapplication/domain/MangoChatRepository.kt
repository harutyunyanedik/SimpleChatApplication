package com.example.mangochatapplication.domain

import com.example.mangochatapplication.domain.model.checkcode.CheckAuthCode
import com.example.mangochatapplication.common.utils.Recourse
import com.example.mangochatapplication.domain.model.profile.ProfileData
import com.example.mangochatapplication.domain.model.registration.Registration
import kotlinx.coroutines.flow.Flow

interface MangoChatRepository {
    suspend fun sendAuthCode(phone: String): Flow<Recourse<Boolean>>

    suspend fun checkAuthCode(phone: String, code: String): Flow<Recourse<CheckAuthCode>>

    suspend fun register(name: String, phone: String, username: String): Flow<Recourse<Registration>>

    suspend fun me(): Flow<Recourse<ProfileData>>
}