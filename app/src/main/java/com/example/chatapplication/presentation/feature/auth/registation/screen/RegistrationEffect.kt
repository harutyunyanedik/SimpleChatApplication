package com.example.chatapplication.presentation.feature.auth.registation.screen

import com.example.chatapplication.domain.model.registration.Registration
import com.example.chatapplication.presentation.base.viewmodel.BaseEffect

sealed class RegistrationEffect : BaseEffect {
    data class Registered(val data: Registration? = null, val error: String? = null) : RegistrationEffect()

    data class Validated(val isValid: Boolean) : RegistrationEffect()

    data object TokensSaved : RegistrationEffect()
}