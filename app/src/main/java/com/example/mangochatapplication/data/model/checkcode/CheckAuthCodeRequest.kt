package com.example.mangochatapplication.data.model.checkcode

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CheckAuthCodeRequest(
    @SerialName("code")
    val code: String?,
    @SerialName("phone")
    val phone: String?
)