package com.example.chatapplication.presentation.shared.utils

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import org.koin.androidx.compose.koinViewModel

@SuppressLint("ContextCastToActivity")
@Composable
inline fun <reified T : ViewModel> activityViewModel(): T {
    return koinViewModel<T>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
}