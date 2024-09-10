package com.example.mangochatapplication.presentation.feature.auth.smsverification.screen

import com.example.mangochatapplication.presentation.base.viewmodel.BaseState

data class SmsVerificationScreenState(
    val isLoading: Boolean? = null,
    val isEnable: Boolean? = null,
    val error: String? = null,
    val phone: String? = null,
    val resendTime: Int? = null,
    val pinValue: String? = null
) : BaseState