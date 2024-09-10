package com.example.mangochatapplication.presentation.feature.chat.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.example.mangochatapplication.presentation.feature.chat.chatappbar.ChatAppBar
import com.example.mangochatapplication.presentation.feature.chat.chatinput.ChatInput
import com.example.mangochatapplication.presentation.shared.utils.activityViewModel
import com.example.mangochatapplication.presentation.shared.viewmodel.profile.ProfileViewModel

@Composable
fun ChatScreen(profileViewModel: ProfileViewModel = activityViewModel()) {

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        ChatAppBar()
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .weight(1f, fill = true)
        ) {
            ChatLazyColumn()
        }
        ChatInput(
            onMessageChange = { },
            onFocusEvent = {}
        )
    }
}

@Composable
fun ChatLazyColumn() {

    val messages = remember {
        listOf("bla", "bla", "bla", "bla", "bla", "bla", "bla")
    }
    val isMessageFromOpponent by remember {
        mutableStateOf(false)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        items(messages.size) { position ->
            when (isMessageFromOpponent) {
                true -> {
                    Text(text = messages[position])
                }

                false -> {
                    Text(text = messages[position], textAlign = TextAlign.End)
                }
            }
        }
    }
}