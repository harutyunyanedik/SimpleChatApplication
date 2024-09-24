package com.example.chatapplication.presentation.feature.registation.screen

import com.example.chatapplication.presentation.base.viewmodel.BaseState

data class RegistrationState(
    val isLoading: Boolean? = null,
    val error: String? = null,
    val phone: String? = null,
    val nameValue: String? = null,
    val usernameValue: String? = null,
    val nameErrorText: String? = null,
    val usernameErrorText: String? = null
) : BaseState