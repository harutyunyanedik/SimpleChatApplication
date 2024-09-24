package com.example.chatapplication.data.model.checkcode

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CheckCodeErrorDto(
    @SerialName("detail")
    val detail: DetailDto?
)

@Serializable
data class DetailDto(
    @SerialName("message")
    val message: String?
)