package com.example.mangochatapplication.presentation.feature.auth.registation.screen

import com.example.mangochatapplication.presentation.base.viewmodel.BaseState

data class RegistrationState(
    val isLoading: Boolean? = null,
    val error: String? = null,
    val phone: String? = null,
    val nameValue: String? = null,
    val usernameValue: String? = null,
    val nameErrorText: String? = null,
    val usernameErrorText: String? = null
) : BaseState