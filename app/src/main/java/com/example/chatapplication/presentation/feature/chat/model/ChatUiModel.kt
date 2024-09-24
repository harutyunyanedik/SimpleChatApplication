package com.example.chatapplication.presentation.feature.chat.model


data class ChatUiModel(
    val messages: List<Message>,
    val addressee: Author,
) {
    data class Message(
        val text: String,
        val author: Author,
    ) {
        val isFromMe: Boolean
            get() = author.id == MY_ID
    }

    data class Author(
        val id: String,
        val name: String,
    ) {
        companion object {
            val bot = Author("1", "Bot")
            val me = Author(MY_ID, "Me")
        }
    }

    companion object {
        const val MY_ID = "-1"
    }
}