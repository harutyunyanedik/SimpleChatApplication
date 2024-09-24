package com.example.chatapplication.presentation.feature.auth.smsverification.screen

import com.example.chatapplication.domain.model.checkcode.CheckAuthCode
import com.example.chatapplication.presentation.base.viewmodel.BaseEffect

sealed class SmsVerificationScreenEffect : BaseEffect {
    data class CodeSent(val isSuccess: Boolean = false) : SmsVerificationScreenEffect()

    data class Verified(val data: CheckAuthCode? = null, val error: String? = null) : SmsVerificationScreenEffect()

    data object TokensSaved : SmsVerificationScreenEffect()
}