package com.example.chatapplication.domain.model.checkcode

data class CheckAuthCode(
    val accessToken: String?,
    val isUserExists: Boolean?,
    val refreshToken: String?,
    val userId: Int?
)