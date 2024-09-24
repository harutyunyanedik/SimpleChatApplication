package com.example.chatapplication.data.model.checkcode

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CheckAuthCodeDto(
    @SerialName("access_token")
    val accessToken: String?,
    @SerialName("is_user_exists")
    val isUserExists: Boolean?,
    @SerialName("refresh_token")
    val refreshToken: String?,
    @SerialName("user_id")
    val userId: Int?
)