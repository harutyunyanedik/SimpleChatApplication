package com.example.chatapplication.domain.model.checkcode

import java.io.Serializable

data class CheckAuthCode(
    val accessToken: String?,
    val isUserExists: Boolean?,
    val refreshToken: String?,
    val userId: Int?
): Serializable