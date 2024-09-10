package com.example.mangochatapplication.presentation.shared.viewmodel.profile

import com.example.mangochatapplication.domain.model.profile.ProfileData
import com.example.mangochatapplication.presentation.base.viewmodel.BaseState

data class ProfileState(
    val isLoading: Boolean? = null,
    val error: String? = null,
    val data: ProfileData? = null
): BaseState
