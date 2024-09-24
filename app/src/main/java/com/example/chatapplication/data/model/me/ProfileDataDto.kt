package com.example.chatapplication.data.model.me


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileDataDto(
    @SerialName("avatar")
    val avatar: String?,
    @SerialName("avatars")
    val avatars: AvatarsDto?,
    @SerialName("birthday")
    val birthday: String?,
    @SerialName("city")
    val city: String?,
    @SerialName("completed_task")
    val completedTask: Int?,
    @SerialName("created")
    val created: String?,
    @SerialName("id")
    val id: Int?,
    @SerialName("instagram")
    val instagram: String?,
    @SerialName("last")
    val last: String?,
    @SerialName("name")
    val name: String?,
    @SerialName("online")
    val online: Boolean?,
    @SerialName("phone")
    val phone: String?,
    @SerialName("status")
    val status: String?,
    @SerialName("username")
    val username: String?,
    @SerialName("vk")
    val vk: String?
)