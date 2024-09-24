package com.example.chatapplication.presentation.feature.auth.phonenumber.screen

import com.example.chatapplication.presentation.base.viewmodel.BaseIntent
import com.example.chatapplication.presentation.shared.model.CountriesEnum

sealed class PhoneNumberScreenIntent : BaseIntent {
    data class CountryChanged(val code: CountriesEnum) : PhoneNumberScreenIntent()

    data class PhoneNumberChanged(val phoneNumber: String) : PhoneNumberScreenIntent()

    data object Validate : PhoneNumberScreenIntent()
}