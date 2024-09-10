package com.example.mangochatapplication.presentation.feature.auth.registation.screen

import com.example.mangochatapplication.domain.model.registration.Registration
import com.example.mangochatapplication.presentation.base.viewmodel.BaseEffect

sealed class RegistrationEffect : BaseEffect {
    data class Registered(val data: Registration? = null, val error: String? = null) : RegistrationEffect()

    data class Validated(val isValid: Boolean) : RegistrationEffect()
}