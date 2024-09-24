package com.example.chatapplication.presentation.feature.phonenumber.screen

import com.example.chatapplication.presentation.base.viewmodel.BaseState
import com.example.chatapplication.presentation.shared.model.CountriesEnum

data class PhoneNumberScreenState(
    val selectedCountry: CountriesEnum = CountriesEnum.RUSSIA,
    val phoneNumber: String? = null,
    val error: String? = null
) : BaseState