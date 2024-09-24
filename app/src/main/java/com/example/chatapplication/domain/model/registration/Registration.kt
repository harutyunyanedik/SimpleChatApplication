package com.example.chatapplication.domain.model.registration

import java.io.Serializable

data class Registration(
    val accessToken: String?,
    val refreshToken: String?,
    val userId: Int?
) : Serializable