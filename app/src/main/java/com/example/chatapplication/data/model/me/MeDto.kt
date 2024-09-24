package com.example.chatapplication.data.model.me


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MeDto(
    @SerialName("profile_data")
    val profileData: ProfileDataDto?
)