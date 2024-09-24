package com.example.chatapplication.presentation.feature.registation.screen

import com.example.chatapplication.presentation.base.viewmodel.BaseIntent

sealed class RegistrationIntent : BaseIntent {

    data class Initial(val phone: String?): RegistrationIntent()

    data class Register(val name: String, val phone: String, val username: String) : RegistrationIntent()

    data class NameValueChanged(val value: String) : RegistrationIntent()

    data class UserNameValueChanged(val value: String) : RegistrationIntent()

    data object Validate: RegistrationIntent()

    data class SaveToken(val accessToken: String?, val refreshToken: String?) : RegistrationIntent()
}