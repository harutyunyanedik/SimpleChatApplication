package com.example.mangochatapplication.presentation.feature.chat.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.mangochatapplication.presentation.shared.utils.activityViewModel
import com.example.mangochatapplication.presentation.shared.viewmodel.profile.ProfileViewModel

@Composable
fun ChatScreen(profileViewModel: ProfileViewModel = activityViewModel()) {

    val state = profileViewModel.profileState.collectAsState().value

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = state.data?.name ?: "")
    }
}