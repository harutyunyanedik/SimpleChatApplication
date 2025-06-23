package com.example.chatapplication.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun ChatApplicationTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        else -> DarkColorScheme
    }
    val systemUiController = rememberSystemUiController()
    val view = LocalView.current

    CompositionLocalProvider(
        LocalSpacing provides LocalScreen.current,
    ) {
        if (!view.isInEditMode) {
            SideEffect {
                systemUiController.setStatusBarColor(colorScheme.background)
                systemUiController.setNavigationBarColor(colorScheme.background)
            }
        }

        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}