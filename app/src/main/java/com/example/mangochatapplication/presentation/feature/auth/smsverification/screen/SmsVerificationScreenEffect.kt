package com.example.mangochatapplication.presentation.feature.auth.smsverification.screen

import com.example.mangochatapplication.domain.model.checkcode.CheckAuthCode
import com.example.mangochatapplication.presentation.base.viewmodel.BaseEffect

sealed class SmsVerificationScreenEffect : BaseEffect {
    data class CodeSent(val isSuccess: Boolean = false) : SmsVerificationScreenEffect()

    data class Verified(val data: CheckAuthCode? = null, val error: String? = null) : SmsVerificationScreenEffect()

    data object TokensSaved : SmsVerificationScreenEffect()
}