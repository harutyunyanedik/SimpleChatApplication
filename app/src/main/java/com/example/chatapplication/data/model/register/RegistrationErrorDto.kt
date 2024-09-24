package com.example.chatapplication.data.model.register

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegistrationErrorDto(
    @SerialName("detail")
    val detail: DetailDto?
)

@Serializable
data class DetailDto(
    @SerialName("message")
    val message: String?
)