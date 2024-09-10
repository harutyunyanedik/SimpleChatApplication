package com.example.mangochatapplication.presentation.shared.viewmodel.profile

import com.example.mangochatapplication.presentation.base.viewmodel.BaseIntent

sealed class ProfileIntent : BaseIntent {
    data object GetMe : ProfileIntent()
}