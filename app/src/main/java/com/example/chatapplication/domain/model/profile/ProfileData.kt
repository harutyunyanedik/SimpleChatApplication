package com.example.chatapplication.domain.model.profile

import com.example.chatapplication.data.model.me.AvatarsDto
import java.io.Serializable

data class ProfileData(
    val avatar: String?,
    val avatars: AvatarsDto?,
    val birthday: String?,
    val city: String?,
    val completedTask: Int?,
    val created: String?,
    val id: Int?,
    val instagram: String?,
    val last: String?,
    val name: String?,
    val online: Boolean?,
    val phone: String?,
    val status: String?,
    val username: String?,
    val vk: String?
) : Serializable