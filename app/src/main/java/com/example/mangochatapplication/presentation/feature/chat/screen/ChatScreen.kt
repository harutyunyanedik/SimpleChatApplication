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
import androidx.navigation.NavHostController
import com.example.mangochatapplication.presentation.feature.chat.chatappbar.ChatAppBar
import com.example.mangochatapplication.presentation.feature.chat.chatinput.ChatInput
import com.example.mangochatapplication.presentation.navigation.routes.Screens

@Composable
fun ChatScreen(navController: NavHostController?) {

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        ChatAppBar(onUserProfilePictureClick = {
            navController?.navigate(Screens.Profile.route)
        })
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