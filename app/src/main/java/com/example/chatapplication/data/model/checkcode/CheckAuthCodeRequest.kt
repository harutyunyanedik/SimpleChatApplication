package com.example.chatapplication.data.model.checkcode

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CheckAuthCodeRequest(
    @SerialName("phone")
    val phone: String?,
    @SerialName("code")
    val code: String?
)