package com.example.chatapplication.presentation.feature.auth.smsverification.screen

import com.example.chatapplication.presentation.base.viewmodel.BaseIntent

sealed class SmsVerificationScreenIntent : BaseIntent {
    data class Initial(val phone: String) : SmsVerificationScreenIntent()

    data class UpdateResendTimer(val tick: Int?) : SmsVerificationScreenIntent()

    data class SendCode(val phone: String) : SmsVerificationScreenIntent()

    data class UpdatePinValue(val value: String) : SmsVerificationScreenIntent()

    data class Verify(val phone: String, val code: String) : SmsVerificationScreenIntent()

    data class SaveToken(val accessToken: String?, val refreshToken: String?) : SmsVerificationScreenIntent()
}