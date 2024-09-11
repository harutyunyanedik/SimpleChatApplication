package com.example.mangochatapplication.presentation.feature.auth.phonenumber.screen

import com.example.mangochatapplication.presentation.base.viewmodel.BaseIntent
import com.example.mangochatapplication.presentation.shared.model.CountriesEnum

sealed class PhoneNumberScreenIntent : BaseIntent {
    data class CountryChanged(val code: CountriesEnum) : PhoneNumberScreenIntent()

    data class PhoneNumberChanged(val phoneNumber: String) : PhoneNumberScreenIntent()

    data object Validate : PhoneNumberScreenIntent()
}