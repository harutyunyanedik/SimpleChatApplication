package com.example.chatapplication.data.model.register

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName


@Serializable
data class RegistrationRequest(
    @SerialName("phone")
    val phone: String?,
    @SerialName("name")
    val name: String?,
    @SerialName("username")
    val username: String?
)