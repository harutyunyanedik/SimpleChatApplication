package com.example.mangochatapplication.presentation.feature.auth.registation.screen

import com.example.mangochatapplication.presentation.base.viewmodel.BaseIntent

sealed class RegistrationIntent : BaseIntent {

    data class Initial(val phone: String?): RegistrationIntent()

    data class Register(val name: String, val phone: String, val username: String) : RegistrationIntent()

    data class NameValueChanged(val value: String) : RegistrationIntent()

    data class UserNameValueChanged(val value: String) : RegistrationIntent()

    data object Validate: RegistrationIntent()
}