package com.example.chatapplication.presentation.feature.chat.screen

import com.example.chatapplication.presentation.base.viewmodel.BaseState
import com.example.chatapplication.presentation.feature.chat.model.ChatUiModel

data class ChatScreenState(val messages: List<ChatUiModel.Message> = emptyList()) : BaseState