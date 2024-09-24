package com.example.chatapplication.presentation.feature.chat.screen

import com.example.chatapplication.presentation.base.viewmodel.BaseIntent

sealed class ChatScreenIntent : BaseIntent {

    data class SendMessage(val message: String) : ChatScreenIntent()
}