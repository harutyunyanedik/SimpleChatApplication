package com.example.chatapplication.presentation.feature.chat

import androidx.lifecycle.viewModelScope
import com.example.chatapplication.presentation.base.viewmodel.BaseViewModel
import com.example.chatapplication.presentation.feature.chat.model.ChatUiModel
import com.example.chatapplication.presentation.feature.chat.screen.ChatScreenIntent
import com.example.chatapplication.presentation.feature.chat.screen.ChatScreenState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChatViewModel : BaseViewModel<ChatScreenIntent, ChatScreenState>() {

    override val states: MutableStateFlow<ChatScreenState> = MutableStateFlow(ChatScreenState())
    val chatState: StateFlow<ChatScreenState>
        get() = states

    override fun handleIntent(intent: ChatScreenIntent) {
        when (intent) {
            is ChatScreenIntent.SendMessage -> {
                sendChat(intent.message)
            }
        }
    }


    private val questions = mutableListOf(
        "What about yesterday?",
        "Can you tell me what inside your head?",
        "Lately, I've been wondering if I can really do anything, do you?",
        "You know fear is often just an illusion, have you ever experienced it?",
        "If you were me, what would you do?"
    )

    private fun sendChat(msg: String) {
        val myChat = ChatUiModel.Message(msg, ChatUiModel.Author.me)
        viewModelScope.launch {
            val newChat = states.value.messages.toMutableList()
            newChat.add(0, myChat)
            updateState(states.value.copy(messages = newChat.toList()))

            delay(1000)
            newChat.add(0, getRandomQuestion())
            updateState(states.value.copy(messages = newChat.toList()))
        }
    }

    private fun getRandomQuestion(): ChatUiModel.Message {
        val question = if (questions.isEmpty()) {
            "no further questions, please leave me alone"
        } else {
            questions.random()
        }

        if (questions.isNotEmpty()) questions.remove(question)

        return ChatUiModel.Message(
            text = question,
            author = ChatUiModel.Author.bot
        )
    }
}