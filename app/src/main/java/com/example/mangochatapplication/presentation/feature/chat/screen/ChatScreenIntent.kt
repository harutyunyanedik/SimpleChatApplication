package com.example.mangochatapplication.presentation.feature.chat.screen

import com.example.mangochatapplication.presentation.base.viewmodel.BaseIntent

sealed class ChatScreenIntent : BaseIntent {

    data class SendMessage(val message: String) : ChatScreenIntent()
}