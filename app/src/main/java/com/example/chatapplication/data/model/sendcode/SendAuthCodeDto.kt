package com.example.chatapplication.data.model.sendcode

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SendAuthCodeDto(
    @SerialName("is_success")
    val isSuccess: Boolean?
)