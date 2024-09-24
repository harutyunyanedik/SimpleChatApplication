package com.example.chatapplication.presentation.feature.smsverification.screen

import com.example.chatapplication.presentation.base.viewmodel.BaseState

data class SmsVerificationScreenState(
    val isLoading: Boolean? = null,
    val isEnable: Boolean? = null,
    val error: String? = null,
    val phone: String? = null,
    val resendTime: Int? = null,
    val pinValue: String? = null
) : BaseState