package com.example.chatapplication.presentation.feature.auth.phonenumber.screen

import com.example.chatapplication.presentation.base.viewmodel.BaseEffect

sealed class PhoneNumberScreenEffect : BaseEffect {
    data class Validated(val error: String?) : PhoneNumberScreenEffect()
}