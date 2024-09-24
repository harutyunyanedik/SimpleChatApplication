package com.example.chatapplication.presentation.shared.viewmodel.profile

import com.example.chatapplication.domain.model.profile.ProfileData
import com.example.chatapplication.presentation.base.viewmodel.BaseState

data class ProfileState(
    val isLoading: Boolean? = null,
    val error: String? = null,
    val data: ProfileData? = null
): BaseState
