package com.example.chatapplication.presentation.shared.viewmodel.profile

import com.example.chatapplication.domain.model.profile.ProfileData
import com.example.chatapplication.presentation.base.viewmodel.BaseEffect

sealed class ProfileEffect : BaseEffect {
    data class ProfileGot(val data: ProfileData? = null, val error: String? = null) : ProfileEffect()
}