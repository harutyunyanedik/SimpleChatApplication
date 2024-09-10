package com.example.mangochatapplication.presentation.shared.viewmodel.profile

import com.example.mangochatapplication.domain.model.profile.ProfileData
import com.example.mangochatapplication.presentation.base.viewmodel.BaseEffect

sealed class ProfileEffect : BaseEffect {
    data class ProfileGot(val data: ProfileData? = null, val error: String? = null) : ProfileEffect()
}