package com.example.mangochatapplication.presentation.feature.auth.phonenumber.screen

import com.example.mangochatapplication.presentation.base.viewmodel.BaseEffect

sealed class PhoneNumberScreenEffect : BaseEffect {
    data class Validated(val error: String?) : PhoneNumberScreenEffect()
}