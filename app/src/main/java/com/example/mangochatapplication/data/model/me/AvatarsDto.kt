package com.example.mangochatapplication.data.model.me


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AvatarsDto(
    @SerialName("avatar")
    val avatar: String?,
    @SerialName("bigAvatar")
    val bigAvatar: String?,
    @SerialName("miniAvatar")
    val miniAvatar: String?
)