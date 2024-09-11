package com.example.mangochatapplication.presentation.feature.chat.screen

import com.example.mangochatapplication.presentation.base.viewmodel.BaseState
import com.example.mangochatapplication.presentation.feature.chat.model.ChatUiModel

data class ChatScreenState(val messages: List<ChatUiModel.Message> = emptyList()) : BaseState