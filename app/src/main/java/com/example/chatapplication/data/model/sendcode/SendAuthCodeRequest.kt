package com.example.chatapplication.data.model.sendcode

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SendAuthCodeRequest(
    @SerialName("phone")
    val phone: String
)
