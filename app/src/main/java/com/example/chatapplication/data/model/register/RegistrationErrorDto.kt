package com.example.chatapplication.data.model.register

import com.example.chatapplication.data.model.checkcode.DetailDto
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