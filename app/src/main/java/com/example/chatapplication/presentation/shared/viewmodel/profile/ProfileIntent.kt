package com.example.chatapplication.presentation.shared.viewmodel.profile

import com.example.chatapplication.presentation.base.viewmodel.BaseIntent

sealed class ProfileIntent : BaseIntent {
    data object GetMe : ProfileIntent()
}