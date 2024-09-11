package com.example.mangochatapplication.presentation.feature.auth.phonenumber.screen

import com.example.mangochatapplication.presentation.base.viewmodel.BaseState
import com.example.mangochatapplication.presentation.shared.model.CountriesEnum

data class PhoneNumberScreenState(
    val selectedCountry: CountriesEnum = CountriesEnum.RUSSIA,
    val phoneNumber: String? = null,
    val error: String? = null
) : BaseState